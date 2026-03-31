package com.interview.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 b_mianshi_detail
 *
 * @author interview
 * @date 2024-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_mianshi_detail")
public class BMianshiDetail extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 数字人语音
     */
    private String humanUrl;
    /**
     * 数字人文本
     */
    private String humanText;
    /**
     * 面试者语音
     */
    private String msUrl;
    /**
     * 面试者文本
     */
    private String msText;
    /**
     * 数字人语音时长
     */
    private String humanDuration;
    /**
     * 面试者语音时长
     */
    private String msDuration;
    /**
     * 答案解析
     */
    private String answerText;
    /**
     * 分数
     */
    private Long score;
    /**
     * 序号
     */
    private Long flag;
    /**
     * 面试id
     */
    private Long msId;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}
