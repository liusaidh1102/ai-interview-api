package com.interview.system.domain.bo;

import com.interview.common.core.validate.AddGroup;
import com.interview.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.interview.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】业务对象 b_mianshi_detail
 *
 * @author interview
 * @date 2024-12-02
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BMianshiDetailBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 数字人语音
     */
    @NotBlank(message = "数字人语音不能为空", groups = { AddGroup.class, EditGroup.class })
    private String humanUrl;

    /**
     * 数字人文本
     */
    @NotBlank(message = "数字人文本不能为空", groups = { AddGroup.class, EditGroup.class })
    private String humanText;

    /**
     * 面试者语音
     */
    @NotBlank(message = "面试者语音不能为空", groups = { AddGroup.class, EditGroup.class })
    private String msUrl;

    /**
     * 面试者文本
     */
    @NotBlank(message = "面试者文本不能为空", groups = { AddGroup.class, EditGroup.class })
    private String msText;

    /**
     * 数字人语音时长
     */
    @NotBlank(message = "数字人语音时长不能为空", groups = { AddGroup.class, EditGroup.class })
    private String humanDuration;

    /**
     * 面试者语音时长
     */
    @NotBlank(message = "面试者语音时长不能为空", groups = { AddGroup.class, EditGroup.class })
    private String msDuration;

    /**
     * 答案解析
     */
    @NotBlank(message = "答案解析不能为空", groups = { AddGroup.class, EditGroup.class })
    private String answerText;

    /**
     * 分数
     */
    @NotNull(message = "分数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long score;

    /**
     * 序号
     */
    @NotNull(message = "序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long flag;

    /**
     * 面试id
     */
    @NotNull(message = "面试id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long msId;


}
