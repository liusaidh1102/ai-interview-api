package com.interview.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 b_jianli
 *
 * @author interview
 * @date 2024-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_jianli")
public class BJianli extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 简历url
     */
    private String url;

    /**
     * 简历名称
     */
    private String name;

    /**
     * 简历解析内容
     */
    private String content;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}
