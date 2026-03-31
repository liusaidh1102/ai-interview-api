package com.interview.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 b_jobs
 *
 * @author interview
 * @date 2024-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_jobs")
public class BJobs extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 岗位名称
     */
    private String name;
    /**
     * 岗位封面
     */
    private String photo;
    /**
     * 岗位描述
     */
    private String msg;
    /**
     * 岗位标签
     */
    private Long typeId;
    /**
     * 是否热门
     */
    private Long isRemen;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}
