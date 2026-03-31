package com.interview.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.interview.common.annotation.ExcelDictFormat;
import com.interview.common.convert.ExcelDictConvert;
import com.interview.system.domain.BJobs;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;

/**
 * 【请填写功能名称】视图对象 b_mianshi
 *
 * @author interview
 * @date 2024-12-02
 */
@Data
@ExcelIgnoreUnannotated
public class BMianshiVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 面试标题
     */
    @ExcelProperty(value = "面试标题")
    private String title;

    /**
     * 面试录屏url
     */
    @ExcelProperty(value = "面试录屏url")
    private String msUrl;

    /**
     * 面试json内容
     */
    @ExcelProperty(value = "面试json内容")
    private String msContent;

    /**
     * 面试岗位id
     */
    @ExcelProperty(value = "面试岗位id")
    private Long jobsId;

    /**
     * 简历id
     */
    @ExcelProperty(value = "简历id")
    private Long jianliId;

    private BYuyueVo yuyue;

    /**
     * 表情反馈
     */
    @ExcelProperty(value = "表情反馈")
    private String msBiaoqing;

    private String msReport;
    /**
     * 结束时间
     */
    private Date endTime;

    private BJianliVo jianliVo;

    private BJobsVo jobsVo;

    private Date createTime;


}
