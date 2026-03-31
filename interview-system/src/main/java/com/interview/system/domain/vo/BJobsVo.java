package com.interview.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.interview.common.annotation.ExcelDictFormat;
import com.interview.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;

/**
 * 【请填写功能名称】视图对象 b_jobs
 *
 * @author interview
 * @date 2024-12-02
 */
@Data
@ExcelIgnoreUnannotated
public class BJobsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 岗位名称
     */
    @ExcelProperty(value = "岗位名称")
    private String name;

    /**
     * 岗位封面
     */
    @ExcelProperty(value = "岗位封面")
    private String photo;

    /**
     * 岗位描述
     */
    @ExcelProperty(value = "岗位描述")
    private String msg;

    /**
     * 岗位标签
     */
    @ExcelProperty(value = "岗位标签")
    private Long typeId;

    /**
     * 是否热门
     */
    @ExcelProperty(value = "是否热门")
    private Long isRemen;


}
