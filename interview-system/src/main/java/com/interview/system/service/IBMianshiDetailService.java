package com.interview.system.service;

import com.interview.system.domain.BMianshi;
import com.interview.system.domain.BMianshiDetail;
import com.interview.system.domain.vo.BMianshiDetailVo;
import com.interview.system.domain.bo.BMianshiDetailBo;
import com.interview.common.core.page.TableDataInfo;
import com.interview.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author interview
 * @date 2024-12-02
 */
public interface IBMianshiDetailService {

    /**
     * 查询【请填写功能名称】
     */
    BMianshiDetailVo queryById(Long id);

    /**
     * 查询【请填写功能名称】列表
     */
    TableDataInfo<BMianshiDetailVo> queryPageList(BMianshiDetailBo bo, PageQuery pageQuery);

    /**
     * 查询【请填写功能名称】列表
     */
    List<BMianshiDetailVo> queryList(BMianshiDetailBo bo);

    /**
     * 新增【请填写功能名称】
     */
    Boolean insertByBo(BMianshiDetailBo bo);

    /**
     * 修改【请填写功能名称】
     */
    Boolean updateByBo(BMianshiDetailBo bo);

    String callDifyApi(BMianshi update);

    /**
     * 校验并批量删除【请填写功能名称】信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
