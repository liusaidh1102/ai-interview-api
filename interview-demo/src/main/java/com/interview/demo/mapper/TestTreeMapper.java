package com.interview.demo.mapper;

import com.interview.common.annotation.DataColumn;
import com.interview.common.annotation.DataPermission;
import com.interview.common.core.mapper.BaseMapperPlus;
import com.interview.demo.domain.TestTree;
import com.interview.demo.domain.vo.TestTreeVo;

/**
 * 测试树表Mapper接口
 *
 * @author Lion Li
 * @date 2021-07-26
 */
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
})
public interface TestTreeMapper extends BaseMapperPlus<TestTreeMapper, TestTree, TestTreeVo> {

}
