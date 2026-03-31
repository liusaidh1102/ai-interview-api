package com.interview.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author gwj
 * @date 2024/9/19 10:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("v_ai_xsxx")
public class VGxfnaiXsjbxx {
    /**
     * 学号
     */
    @TableId(type = IdType.INPUT)
    private Long studentId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 民族
     */
    private String nation;
    /**
     * 政治面貌
     */
    private String politicalOutlook;
    /**
     * 电话
     */
    private String contact;
    /**
     * 学院
     */
    private String faculty;
    private String facultyId;
    /**
     * 性别
     */
    private String sex;
    /**
     * 班级
     */
    @TableField("class")
    private String classs;
    private String classId;
    /**
     * 专业
     */
    private String speciality;
    private String SpecialityId;
}
