package com.interview.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.interview.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学生成绩对象 v_gxfnai_cjxf
 *
 * @author aiUniversity
 * @date 2024-09-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("v_ai_cjxf")
public class VGxfnaiCjxf extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    private String studentId;
    /**
     *
     */
    private String courseId;
    /**
     *
     */
    private Double credit;
    /**
     *
     */
    private String score;
    /**
     *
     */
    private String gradeYear;
    /**
     *
     */
    private Integer gradeTerm;

    private String type;

}
