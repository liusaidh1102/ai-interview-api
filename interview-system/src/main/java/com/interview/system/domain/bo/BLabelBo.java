package com.interview.system.domain.bo;

import com.interview.common.core.validate.AddGroup;
import com.interview.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】业务对象 b_label
 *
 * @author interview
 * @date 2024-12-02
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BLabelBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long flag;


}
