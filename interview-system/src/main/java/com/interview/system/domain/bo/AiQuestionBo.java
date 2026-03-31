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
public class AiQuestionBo {
    /**
     * 简历url
     */
    @NotBlank(message = "job不能为空", groups = { AddGroup.class, EditGroup.class })
    private String job;

    private Integer count;

//    /**
//     * 简历解析内容
//     */
//    @NotBlank(message = "简历解析内容不能为空", groups = { AddGroup.class, EditGroup.class })
//    private String audioUrl;
}
