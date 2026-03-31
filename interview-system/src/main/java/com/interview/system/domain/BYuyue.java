package com.interview.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.interview.common.core.domain.BaseEntity;

/**
 * 预约对象 b_yuyue
 *
 * @author interview
 * @date 2024-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_yuyue")
public class BYuyue extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 预约时间
     */
    private Date yyTime;
    /**
     * 面试岗位id
     */
    private Long jobsId;

    /**
     * 简历id
     */
    private Long jianliId;
    private Long msId;
    private Long qiyeId;

    private String qiyeName;

    private String userFullname;

    private Integer msType;

    private Integer msStatus;

    private String jobsTitle;

    private String msTitle;

    private String unionUserId;
    private String resumePostId;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

}
