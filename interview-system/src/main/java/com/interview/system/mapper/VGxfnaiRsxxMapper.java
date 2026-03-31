package com.interview.system.mapper;

import com.interview.common.core.mapper.BaseMapperPlus;
import com.interview.system.domain.VGxfnaiRsxx;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.interview.system.domain.VGxfnaiRsxxVO;
import org.apache.ibatis.annotations.Mapper;

@DS("hky")
@Mapper
public interface VGxfnaiRsxxMapper extends BaseMapperPlus<VGxfnaiRsxxMapper , VGxfnaiRsxx, VGxfnaiRsxxVO> {
}
