package com.interview.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 b_mianshi_bq
 *
 * @author interview
 * @date 2025-03-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_mianshi_bq")
public class BMianshiBq extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 面试id
     */
    private Long msId;
    /**
     * 序号
     */
    @TableField("`index`")
    private Long index;
    /**
     * 表情反馈
     */
    private String content;
    /**
     * 排序
     */
    private Long sort;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
    /**
     * 面试视频url
     */
    private String url;

}
