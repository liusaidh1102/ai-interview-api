package com.interview.system.domain.vo;

import com.interview.system.domain.VGxfnaiXsjbxx;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author gwj
 * @create 2024/9/19 16:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VGxfnaiXsjbxxVO extends VGxfnaiXsjbxx {
//    private Integer credit;
//    private String grade;
    private boolean isDanger = false;

    public VGxfnaiXsjbxxVO(VGxfnaiXsjbxx vGxfnaiXsjbxx) {
        this.setStudentId(vGxfnaiXsjbxx.getStudentId());
        this.setName(vGxfnaiXsjbxx.getName());
        this.setFaculty(vGxfnaiXsjbxx.getFaculty());
        this.setClasss(vGxfnaiXsjbxx.getClasss());
        Long id = vGxfnaiXsjbxx.getStudentId();
//        this.setGrade(String.valueOf(id).substring(0, 4));
//        this.setCredit(credit);
    }
}
