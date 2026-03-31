package com.interview.system.service;

import com.interview.system.domain.BJobs;
import com.interview.system.domain.vo.BJobsVo;
import com.interview.system.domain.bo.BJobsBo;
import com.interview.common.core.page.TableDataInfo;
import com.interview.common.core.domain.PageQuery;
import com.interview.system.domain.vo.BLabelVo;

import java.util.Collection;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author interview
 * @date 2024-12-02
 */
public interface IBJobsService {

    /**
     * 查询【请填写功能名称】
     */
    BJobsVo queryById(Long id);

    /**
     * 查询【请填写功能名称】列表
     */
    TableDataInfo<BJobsVo> queryPageList(BJobsBo bo, PageQuery pageQuery);

    /**
     * 查询【请填写功能名称】列表
     */
    List<BJobsVo> queryList(BJobsBo bo);
    List<BLabelVo>  queryAllList(BJobsBo bo);

    /**
     * 新增【请填写功能名称】
     */
    Boolean insertByBo(BJobsBo bo);

    /**
     * 修改【请填写功能名称】
     */
    Boolean updateByBo(BJobsBo bo);

    /**
     * 校验并批量删除【请填写功能名称】信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
