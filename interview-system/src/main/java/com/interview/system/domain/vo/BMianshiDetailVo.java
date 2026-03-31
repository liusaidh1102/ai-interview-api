package com.interview.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.interview.common.annotation.ExcelDictFormat;
import com.interview.common.convert.ExcelDictConvert;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;

/**
 * 【请填写功能名称】视图对象 b_mianshi_detail
 *
 * @author interview
 * @date 2024-12-02
 */
@Data
@ExcelIgnoreUnannotated
public class BMianshiDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 数字人语音
     */
    @ExcelProperty(value = "数字人语音")
    private String humanUrl;

    /**
     * 数字人文本
     */
    @ExcelProperty(value = "数字人文本")
    private String humanText;

    /**
     * 面试者语音
     */
    @ExcelProperty(value = "面试者语音")
    private String msUrl;

    /**
     * 面试者文本
     */
    @ExcelProperty(value = "面试者文本")
    private String msText;

    /**
     * 数字人语音时长
     */
    @ExcelProperty(value = "数字人语音时长")
    private String humanDuration;

    /**
     * 面试者语音时长
     */
    @ExcelProperty(value = "面试者语音时长")
    private String msDuration;

    /**
     * 答案解析
     */
    @ExcelProperty(value = "答案解析")
    private String answerText;

    /**
     * 分数
     */
    @ExcelProperty(value = "分数")
    private Long score;

    /**
     * 序号
     */
    @ExcelProperty(value = "序号")
    private Long flag;

    /**
     * 面试id
     */
    @ExcelProperty(value = "面试id")
    private Long msId;


}
