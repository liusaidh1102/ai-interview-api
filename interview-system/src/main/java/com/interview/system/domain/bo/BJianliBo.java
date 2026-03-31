package com.interview.system.domain.bo;

import com.interview.common.core.validate.AddGroup;
import com.interview.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】业务对象 b_jianli
 *
 * @author interview
 * @date 2024-12-02
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BJianliBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 简历名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 简历url
     */
    @NotBlank(message = "简历url不能为空", groups = { AddGroup.class, EditGroup.class })
    private String url;

    /**
     * 简历解析内容
     */
    @NotBlank(message = "简历解析内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;


}
