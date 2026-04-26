package com.interview.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.interview.common.annotation.Log;
import com.interview.common.annotation.RepeatSubmit;
import com.interview.common.core.controller.BaseController;

import java.util.concurrent.Future;

import com.interview.common.core.domain.R;
import com.interview.common.core.validate.AddGroup;
import com.interview.common.enums.BusinessType;
import com.interview.common.helper.LoginHelper;
import com.interview.common.utils.redis.RedisUtils;
import com.interview.system.domain.bo.*;
import com.interview.system.service.AiService;
import com.interview.system.service.IInterviewTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * ai
 *
 * @author interview
 * @date 2024-12-02
 */
import com.interview.system.service.IInterviewTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * AI 面试核心控制器
 *
 * 架构说明：
 * 1. 采用 Redis 队列异步处理任务，完全废弃 Java 与 Python 之间的直接 HTTP 通信。
 * 2. 支持 ASR (语音识别)、TTS (语音合成)、BQFK (表情分析) 任务的提交。
 * 3. 结果通过 WebSocket 异步推送，并提供 HTTP 状态查询作为兜底。
 *
 * @author interview
 */
@Validated
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/system/ai")
public class AiController extends BaseController {

    private final IInterviewTaskService interviewTaskService;

    /**
     * 语音合成 (TTS)：提交文本到 RabbitMQ 队列
     */
    @Log(title = "TTS 任务提交", businessType = BusinessType.INSERT)
    @PostMapping("tts")
    @SaIgnore
    public R<Object> tts(@Validated(AddGroup.class) @RequestBody AiTtsBo text) {
        Long userId = LoginHelper.getUserId();
        String userKey = userId != null ? String.valueOf(userId) : "anonymous";

        return R.ok(interviewTaskService.submitTts(userKey, text.getText()));
    }

    /**
     * 语音识别 (ASR)：保存音频并提交 RabbitMQ 任务
     */
    @Log(title = "ASR 任务提交", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping("/asr")
    @SaIgnore
    public R<Object> asr(@RequestPart("file") MultipartFile file) {
        try {
            Long userId = LoginHelper.getUserId();
            String userKey = userId != null ? String.valueOf(userId) : "anonymous";

            return R.ok(interviewTaskService.submitAsr(userKey, file));
        } catch (Exception e) {
            log.error("ASR 提交失败", e);
            return R.fail("语音识别提交失败");
        }
    }

    /**
     * 表情识别 (BQFK)：保存视频片段并提交 RabbitMQ 任务
     */
    @Log(title = "BQFK 任务提交", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping("/bqfk")
    @SaIgnore
    public R<Object> bqfk(@RequestPart("file") MultipartFile file,
                          @RequestParam("msId") String msId,
                          @RequestParam("index") String index) {
        try {
            Long userId = LoginHelper.getUserId();
            String userKey = userId != null ? String.valueOf(userId) : "anonymous";

            return R.ok(interviewTaskService.submitBqfk(userKey, file, msId, index));
        } catch (Exception e) {
            log.error("BQFK 提交失败", e);
            return R.fail("表情分析提交失败");
        }
    }

    /**
     * 获取任务状态 (前端兜底轮询)
     */
    @GetMapping("/status/{taskId}")
    public R<Object> getStatus(@PathVariable String taskId) {
        return R.ok(interviewTaskService.getTaskStatus(taskId));
    }
}

//    /**
//     * 修改【请填写功能名称】
//     */
//    @SaCheckPermission("system:label:edit")
//    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
//    @RepeatSubmit()
//    @PutMapping()
//    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BLabelBo bo) {
//        return toAjax(iBLabelService.updateByBo(bo));
//    }
//
//    /**
//     * 删除【请填写功能名称】
//     *
//     * @param ids 主键串
//     */
//    @SaCheckPermission("system:label:remove")
//    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public R<Void> remove(@NotEmpty(message = "主键不能为空")
//                          @PathVariable Long[] ids) {
//        return toAjax(iBLabelService.deleteWithValidByIds(Arrays.asList(ids), true));
//    }
