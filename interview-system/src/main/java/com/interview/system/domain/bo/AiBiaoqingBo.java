package com.interview.system.domain.bo;

import com.interview.common.core.validate.AddGroup;
import com.interview.common.core.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @program: interview-vue-plus
 * @description:
 * @author: sangjinchao
 * @create: 2024-12-10 15:47
 **/
@Data
public class AiBiaoqingBo {
    /**
     * 简历url
     */
    @NotBlank(message = "简历url不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long msId;

    /**
     * 简历解析内容
     */
    @NotBlank(message = "简历解析内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long index;
}
