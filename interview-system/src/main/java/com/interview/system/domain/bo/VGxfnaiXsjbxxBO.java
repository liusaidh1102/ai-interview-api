package com.interview.system.domain.bo;
import lombok.Data;
import javax.validation.constraints.Min;

/**
 * @author gwj
 * @date 2024/11/1 20:45
 */
@Data
public class VGxfnaiXsjbxxBO {
    /**
     * 学号
     */
    private Long studentId;

    /**
     * 姓名
     */
    private String name;

    @Min(1)
    private Integer pageNum;
    @Min(1)
    private Integer pageSize;

    /**
     * 学院
     */
    private String faculty;

    /**
     * 专业
     */
    private String speciality;
}
