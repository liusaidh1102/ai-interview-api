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
public class AiTtsBo {
    /**
     * 文字内容
     */
    @NotBlank(message = "内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String text;

    private String taskId;

}
