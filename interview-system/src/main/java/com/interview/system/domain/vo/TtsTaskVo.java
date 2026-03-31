package com.interview.system.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * TTS任务VO
 */
@Data
public class TtsTaskVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private String taskId;

    /** 任务状态: PENDING-等待中, QUEUED-队列中, PROCESSING-处理中, COMPLETED-完成, FAILED-失败, RATELIMITED-被限流 */
    private String status;

    /** 用户ID */
    private String userId;

    /** 文本内容 */
    private String text;

    /** 音频URL */
    private String audioUrl;

    /** 错误信息 */
    private String errorMsg;

    /** 提交时间 */
    private LocalDateTime submitTime;

    /** 完成时间 */
    private LocalDateTime completeTime;

    /** 预估等待时间(秒) */
    private Integer estimatedWaitSeconds;

    /** 队列位置 */
    private Integer queuePosition;
}
