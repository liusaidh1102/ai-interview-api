package com.interview.system.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.interview.system.ws.TtsWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * TTS 任务服务
 * 集成 Sentinel 实现：熔断、限流（舱壁模式）、超时、重试
 */
@Slf4j
@Service
public class TtsTaskService {

    // Sentinel 资源名称定义
    private static final String RESOURCE_TTS = "ttsSynthesis";
    private static final String RESOURCE_TTS_RETRY = "ttsWithRetry";

    // 重试配置
    private static final int MAX_RETRIES = 3;
    private static final long INITIAL_RETRY_DELAY_MS = 500;

    // TTS 服务地址
    private static final String TTS_SERVICE_URL = "http://localhost:8181/tts?msg=";

    // 自身代理，用于解决同类方法调用注解不生效问题
    @Lazy
    @Autowired
    private TtsTaskService self;

    /**
     * 初始化 Sentinel 规则
     */
    @PostConstruct
    public void initSentinelRules() {
        // ========== 1. 熔断规则（Degrade） ==========
        // 基于错误率的熔断策略
        DegradeRule degradeRule = new DegradeRule(RESOURCE_TTS)
                .setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType())  // 错误率模式
                .setCount(0.5)                                            // 错误率达到 50% 触发熔断
                .setTimeWindow(30)                                        // 熔断持续 30 秒
                .setMinRequestAmount(5)                                   // 最小统计请求数
                .setStatIntervalMs(1000)                                  // 统计时间窗口 1 秒
                .setSlowRatioThreshold(0.8);                              // 慢调用比例阈值

        DegradeRuleManager.loadRules(Collections.singletonList(degradeRule));

        // ========== 2. 限流规则（舱壁模式 - Bulkhead） ==========
        // 限制并发线程数，防止资源耗尽
        FlowRule flowRule = new FlowRule(RESOURCE_TTS)
                .setGrade(0)  // 基于并发线程数限流 (FLOW_GRADE_THREAD = 0)
                .setCount(20)                           // 最大并发 20 个线程（舱壁容量）
                .setStrategy(0)  // 直接 (STRATEGY_DIRECT = 0)
                .setControlBehavior(0);  // 直接拒绝 (CONTROL_BEHAVIOR_DEFAULT = 0)

        FlowRuleManager.loadRules(Collections.singletonList(flowRule));

        log.info("Sentinel rules initialized: circuitBreaker(errorRatio=50%, timeWindow=30s), " +
                "bulkhead(maxThreads=20)");
    }

    /**
     * 提交 TTS 任务（同步入口，处理线程池拒绝）
     * 舱壁模式第一层：线程池隔离
     */
    public void submit(String userId, String taskId, String text) {
        try {
            // 通过代理调用，确保 @Async 和 @SentinelResource 生效
            self.execute(userId, taskId, text);
        } catch (java.util.concurrent.RejectedExecutionException e) {
            log.warn("TTS thread pool full, request rejected: userId={}, taskId={}", userId, taskId);
            TtsWebSocketHandler.push(userId, new Push(taskId, null, "failed",
                    "system busy, please try again later"));
        }
    }

    /**
     * 执行 TTS 任务（异步方法）
     * 舱壁模式第二层：Sentinel 并发限流 + 熔断
     */
    @Async("ttsExecutor")
    @SentinelResource(
            value = RESOURCE_TTS,
            blockHandler = "handleBlock",
            fallback = "handleFallback"
    )
    public void execute(String userId, String taskId, String text) {
        log.info("TTS task started: userId={}, taskId={}", userId, taskId);

        // URL 编码处理
        String encodedText;
        try {
            encodedText = URLEncoder.encode(text, "UTF-8");
        } catch (Exception e) {
            encodedText = text;
        }

        // 带指数退避的重试调用
        JSONObject result = executeWithRetry(userId, taskId, encodedText);

        if (result == null) {
            // 记录异常，触发熔断统计
            Tracer.trace(new RuntimeException("TTS service failed after retries"));

            TtsWebSocketHandler.push(userId, new Push(taskId, null, "failed", "tts service unavailable"));
            log.warn("TTS task failed after retries: userId={}, taskId={}", userId, taskId);
            return;
        }

        // 成功响应
        Object message = result.get("message");
        TtsWebSocketHandler.push(userId, new Push(taskId, String.valueOf(message), "success", null));
        log.info("TTS task completed: userId={}, taskId={}", userId, taskId);
    }

    /**
     * 带指数退避的重试机制
     * 超时时间：5 秒
     */
    private JSONObject executeWithRetry(String userId, String taskId, String encodedText) {
        String url = TTS_SERVICE_URL + encodedText;

        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            try (Entry entry = SphU.entry(RESOURCE_TTS_RETRY)) {
                // 实际 HTTP 调用，5 秒超时
                try (HttpResponse resp = HttpRequest.get(url)
                        .timeout(5000)  // 超时机制：5 秒
                        .execute()) {

                    if (resp.isOk()) {
                        return JSONUtil.parseObj(resp.body());
                    } else {
                        log.warn("TTS HTTP error: status={}, attempt={}/{}",
                                resp.getStatus(), attempt + 1, MAX_RETRIES);
                    }
                }
            } catch (BlockException be) {
                // Sentinel 限流触发
                log.warn("TTS rate limited on attempt {}/{}: {}", attempt + 1, MAX_RETRIES, be.getMessage());
                return null;
            } catch (Exception e) {
                log.warn("TTS request exception, attempt {}/{}: {}",
                        attempt + 1, MAX_RETRIES, e.getMessage());
            }

            // 指数退避：500ms, 1000ms, 2000ms
            if (attempt < MAX_RETRIES - 1) {
                long delay = INITIAL_RETRY_DELAY_MS * (1L << attempt);
                log.debug("Retry delay: {}ms", delay);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        }

        return null;
    }

    /**
     * 舱壁模式触发时的处理（并发线程数超限）
     * 快速失败，防止资源耗尽
     */
    public void handleBlock(String userId, String taskId, String text, BlockException ex) {
        log.warn("TTS bulkhead full, request rejected: userId={}, taskId={}, reason={}",
                userId, taskId, ex.getRule().getResource());

        TtsWebSocketHandler.push(userId, new Push(taskId, null, "failed",
                "system busy, please try again later"));
    }

    /**
     * 业务异常降级处理
     */
    public void handleFallback(String userId, String taskId, String text, Throwable ex) {
        log.error("TTS fallback triggered: userId={}, taskId={}", userId, taskId, ex);

        TtsWebSocketHandler.push(userId, new Push(taskId, null, "failed",
                "service temporarily unavailable"));
    }

    /**
     * WebSocket 推送消息封装
     */
    public static class Push {
        public String taskId;
        public String audioUrl;
        public String status;
        public String error;

        public Push(String taskId, String audioUrl, String status, String error) {
            this.taskId = taskId;
            this.audioUrl = audioUrl;
            this.status = status;
            this.error = error;
        }
    }
}
