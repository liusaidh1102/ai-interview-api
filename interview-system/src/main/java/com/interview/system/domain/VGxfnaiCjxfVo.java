package com.interview.system.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 学生成绩视图对象 v_gxfnai_cjxf
 *
 * @author aiUniversity
 * @date 2024-09-19
 */
@Data
@ExcelIgnoreUnannotated
public class VGxfnaiCjxfVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String studentId;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String courseId;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Double credit;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Double score;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String gradeYear;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Integer gradeTerm;


}
