package com.interview.system.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.interview.common.core.mapper.BaseMapperPlus;
import com.interview.system.domain.VGxfnaiCjxf;
import com.interview.system.domain.VGxfnaiCjxfVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生成绩Mapper接口
 *
 * @author aiUniversity
 * @date 2024-09-19
 */
@DS("hky")
@Mapper
public interface VGxfnaiCjxfMapper extends BaseMapperPlus<VGxfnaiCjxfMapper, VGxfnaiCjxf, VGxfnaiCjxfVo> {


    List<Long> getSameGradeStu(@Param("specialityId") String specialityId);

//    List<RankingBo> getRanking(@Param("studentId") Long studentId, @Param("specialityId") String specialityId);
//
//    List<ScoreVO> getDataByGrade(@Param("gradeYear") String gradeYear, @Param("studentId") Long studentId);
//
//    List<AnalyseVO> selectCourse(@Param("studentId") Long studentId,
//                                 @Param("specialityId") String specialityId);
//
//    List<RankingBo> getDataByYear(@Param("studentId") String studentId,@Param("year") Integer year, @Param("term") Integer term,@Param("specialityId") String specialityId);
}
