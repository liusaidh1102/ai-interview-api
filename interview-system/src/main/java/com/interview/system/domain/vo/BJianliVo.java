package com.interview.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.interview.common.annotation.ExcelDictFormat;
import com.interview.common.convert.ExcelDictConvert;
import com.interview.common.core.validate.AddGroup;
import com.interview.common.core.validate.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

import java.io.Serializable;

/**
 * 【请填写功能名称】视图对象 b_jianli
 *
 * @author interview
 * @date 2024-12-02
 */
@Data
@ExcelIgnoreUnannotated
public class BJianliVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 简历url
     */
    @ExcelProperty(value = "简历url")
    private String url;

    /**
     * 简历名称
     */
    @ExcelProperty(value = "简历名称")
    private String name;

    /**
     * 简历解析内容
     */
    @ExcelProperty(value = "简历解析内容")
    private String content;


}
