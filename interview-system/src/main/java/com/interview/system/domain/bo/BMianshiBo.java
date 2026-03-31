package com.interview.system.domain.bo;

import com.interview.common.core.validate.AddGroup;
import com.interview.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】业务对象 b_mianshi
 *
 * @author interview
 * @date 2024-12-02
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BMianshiBo extends BaseEntity {

    /**
     * 面试id
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 面试标题
     */
    @NotBlank(message = "面试标题不能为空", groups = { AddGroup.class })
    private String title;

    /**
     * 面试录屏url
     */
//    @NotBlank(message = "面试录屏url不能为空", groups = { AddGroup.class, EditGroup.class })
    private String msUrl;

    /**
     * 面试json内容
     */
//    @NotBlank(message = "面试json内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String msContent;

    /**
     * 面试岗位id
     */
//    @NotNull(message = "面试岗位id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long jobsId;

    /**
     * 简历id
     */
//    @NotNull(message = "简历id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long jianliId;

    private Long yuyueId;

    /**
     * 表情反馈
     */
//    @NotBlank(message = "表情反馈不能为空", groups = { AddGroup.class, EditGroup.class })
    private String msBiaoqing;

    private String msReport;

    /**
     * 结束时间
     */
    private Date endTime;


}
