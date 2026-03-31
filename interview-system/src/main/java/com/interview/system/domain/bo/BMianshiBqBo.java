package com.interview.system.domain.bo;

import com.interview.common.core.validate.AddGroup;
import com.interview.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】业务对象 b_mianshi_bq
 *
 * @author interview
 * @date 2025-03-12
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BMianshiBqBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 面试id
     */
    @NotNull(message = "面试id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long msId;

    /**
     * 序号
     */
    @NotNull(message = "序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long index;

    /**
     * 表情反馈
     */
    @NotBlank(message = "表情反馈不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sort;

    /**
     * 面试视频url
     */
    @NotBlank(message = "面试视频url不能为空", groups = { AddGroup.class, EditGroup.class })
    private String url;


}
