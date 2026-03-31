package com.interview.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.interview.common.annotation.ExcelDictFormat;
import com.interview.common.convert.ExcelDictConvert;
import com.interview.system.domain.BJobs;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;
import java.util.List;

/**
 * 【请填写功能名称】视图对象 b_label
 *
 * @author interview
 * @date 2024-12-02
 */
@Data
@ExcelIgnoreUnannotated
public class BLabelVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 标签名称
     */
    @ExcelProperty(value = "标签名称")
    private String type;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long flag;

    private List<BJobsVo> jobs;


}
