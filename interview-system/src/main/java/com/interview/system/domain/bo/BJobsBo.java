package com.interview.system.domain.bo;

import com.interview.common.core.validate.AddGroup;
import com.interview.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】业务对象 b_jobs
 *
 * @author interview
 * @date 2024-12-02
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BJobsBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 岗位名称
     */
    @NotBlank(message = "岗位名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 岗位封面
     */
    @NotBlank(message = "岗位封面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String photo;

    /**
     * 岗位描述
     */
    @NotBlank(message = "岗位描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String msg;

    /**
     * 岗位标签
     */
    @NotNull(message = "岗位标签不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long typeId;

    /**
     * 是否热门
     */
    @NotNull(message = "是否热门不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long isRemen;


}
