package com.interview.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 b_label
 *
 * @author interview
 * @date 2024-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_label")
public class BLabel extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 标签名称
     */
    private String type;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
    /**
     * 排序
     */
    private Long flag;

}
