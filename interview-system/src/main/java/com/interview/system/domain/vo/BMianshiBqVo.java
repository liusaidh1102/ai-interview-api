package com.interview.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.interview.common.annotation.ExcelDictFormat;
import com.interview.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;

/**
 * 【请填写功能名称】视图对象 b_mianshi_bq
 *
 * @author interview
 * @date 2025-03-12
 */
@Data
@ExcelIgnoreUnannotated
public class BMianshiBqVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 面试id
     */
    @ExcelProperty(value = "面试id")
    private Long msId;

    /**
     * 序号
     */
    @ExcelProperty(value = "序号")
    private Long index;

    /**
     * 表情反馈
     */
    @ExcelProperty(value = "表情反馈")
    private String content;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long sort;

    /**
     * 面试视频url
     */
    @ExcelProperty(value = "面试视频url")
    private String url;


}
