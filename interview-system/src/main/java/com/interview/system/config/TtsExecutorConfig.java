package com.interview.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync(proxyTargetClass = true)
public class TtsExecutorConfig {
    @Bean("ttsExecutor")
    public Executor ttsExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 舱壁模式：限制并发线程数
        executor.setCorePoolSize(10);      // 核心线程数
        executor.setMaxPoolSize(20);       // 最大线程数（与 Sentinel 限流阈值一致）
        executor.setQueueCapacity(50);     // 等待队列容量（小队列，快速失败）

        // 关键：队列满时直接拒绝，实现快速失败
        executor.setRejectedExecutionHandler(
                new java.util.concurrent.ThreadPoolExecutor.AbortPolicy()
        );

        executor.setThreadNamePrefix("tts-");

        // 优雅关闭配置
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);

        executor.initialize();
        return executor;
    }
}
