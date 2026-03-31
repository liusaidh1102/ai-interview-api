package com.interview.system.service;

import com.interview.system.domain.BJianli;
import com.interview.system.domain.vo.BJianliVo;
import com.interview.system.domain.bo.BJianliBo;
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
public interface IBJianliService {

    /**
     * 查询【请填写功能名称】
     */
    BJianliVo queryById(Long id);

    /**
     * 查询【请填写功能名称】列表
     */
    TableDataInfo<BJianliVo> queryPageList(BJianliBo bo, PageQuery pageQuery);

    /**
     * 查询【请填写功能名称】列表
     */
    List<BJianliVo> queryList(BJianliBo bo);

    /**
     * 新增【请填写功能名称】
     */
    BJianli insertByBo(BJianliBo bo);

    /**
     * 修改【请填写功能名称】
     */
    Boolean updateByBo(BJianliBo bo);

    /**
     * 校验并批量删除【请填写功能名称】信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
