package com.interview.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 b_mianshi
 *
 * @author interview
 * @date 2024-12-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("b_mianshi")
public class BMianshi extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 面试标题
     */
    private String title;
    /**
     * 面试录屏url
     */
    private String msUrl;
    /**
     * 面试json内容
     */
    private String msContent;
    /**
     * 面试岗位id
     */
    private Long jobsId;
    /**
     * 简历id
     */
    private Long jianliId;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;
    /**
     * 表情反馈
     */
    private String msBiaoqing;

    /**
     * dify返回的面试报告结果的json字符串
     */
    private String msReport;

    /**
     * 结束时间
     */
    private Date endTime;

}
