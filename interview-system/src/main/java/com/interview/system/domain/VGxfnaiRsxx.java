package com.interview.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("v_ai_rsxx")
public class VGxfnaiRsxx {
    @TableId(type = IdType.INPUT)
    private Long teacherId;
    private String teacherName;
    private String zhicheng;
    private String zhiwei;
    private String department;
    private String departmentId;
}
