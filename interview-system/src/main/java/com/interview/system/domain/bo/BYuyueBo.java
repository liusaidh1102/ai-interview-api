package com.interview.system.domain.bo;

import com.interview.common.core.validate.AddGroup;
import com.interview.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.interview.common.core.domain.BaseEntity;

/**
 * 预约业务对象 b_yuyue
 *
 * @author interview
 * @date 2024-12-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BYuyueBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 预约时间
     */
    @NotNull(message = "预约时间不能为空", groups = { AddGroup.class, EditGroup.class })
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


}
