package com.interview.system.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.interview.common.core.mapper.BaseMapperPlus;
import com.interview.system.domain.VGxfnaiXsjbxx;
import com.interview.system.domain.bo.VGxfnaiXsjbxxBO;
import com.interview.system.domain.vo.StudentVO;
import com.interview.system.domain.vo.VGxfnaiXsjbxxVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gwj
 * @create 2024/9/19 15:55
 */
@Mapper
@DS("hky")
public interface VGxfnaiXsjbxxMapper extends BaseMapperPlus<VGxfnaiXsjbxxMapper, VGxfnaiXsjbxx, VGxfnaiXsjbxxVO> {
//    Page<StudentVO> selectStu(@Param("page") Page<StudentVO> page, @Param("bo") VGxfnaiXsjbxxBO bo);
//    Integer selectSumCredit(@Param("studentId") Long studentId);
//    List<String> selectFaculty();
//
//    List<String> selectSpeciality(@Param("faculty") String faculty);
}
