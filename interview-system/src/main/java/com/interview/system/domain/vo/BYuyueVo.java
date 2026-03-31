package com.interview.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.interview.common.annotation.ExcelDictFormat;
import com.interview.common.convert.ExcelDictConvert;
import com.interview.system.domain.BJianli;
import com.interview.system.domain.BJobs;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;

/**
 * 预约视图对象 b_yuyue
 *
 * @author interview
 * @date 2024-12-18
 */
@Data
@ExcelIgnoreUnannotated
public class BYuyueVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 预约时间
     */
    @ExcelProperty(value = "预约时间")
    private Date yyTime;

    /**
     * 面试岗位id
     */
    private Long jobsId;

    /**
     * 简历id
     */
    private Long jianliId;

    private Long msId;

    private Long qiyeId;

    private String qiyeName;

    private String userFullname;

    private Integer msType;

    private Integer msStatus;

    private String jobsTitle;

    private String msTitle;

    private String unionUserId;

    private String resumePostId;

    private String createBy;

    private BJianli jianli;

    private BJobs jobs;


}
