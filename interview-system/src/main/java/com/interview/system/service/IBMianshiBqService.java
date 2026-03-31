package com.interview.system.service;

import com.interview.system.domain.BMianshiBq;
import com.interview.system.domain.vo.BMianshiBqVo;
import com.interview.system.domain.bo.BMianshiBqBo;
import com.interview.common.core.page.TableDataInfo;
import com.interview.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author interview
 * @date 2025-03-12
 */
public interface IBMianshiBqService {

    /**
     * 查询【请填写功能名称】
     */
    BMianshiBqVo queryById(Long id);

    /**
     * 查询【请填写功能名称】列表
     */
    TableDataInfo<BMianshiBqVo> queryPageList(BMianshiBqBo bo, PageQuery pageQuery);

    /**
     * 查询【请填写功能名称】列表
     */
    List<BMianshiBqVo> queryList(BMianshiBqBo bo);

    /**
     * 新增【请填写功能名称】
     */
    Boolean insertByBo(BMianshiBqBo bo);

    /**
     * 修改【请填写功能名称】
     */
    Boolean updateByBo(BMianshiBqBo bo);

    /**
     * 校验并批量删除【请填写功能名称】信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
