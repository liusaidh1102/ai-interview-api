package com.interview.system.service;

import com.interview.system.domain.BLabel;
import com.interview.system.domain.vo.BLabelVo;
import com.interview.system.domain.bo.BLabelBo;
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
public interface IBLabelService {

    /**
     * 查询【请填写功能名称】
     */
    BLabelVo queryById(Long id);

    /**
     * 查询【请填写功能名称】列表
     */
    TableDataInfo<BLabelVo> queryPageList(BLabelBo bo, PageQuery pageQuery);

    /**
     * 查询【请填写功能名称】列表
     */
    List<BLabelVo> queryList(BLabelBo bo);

    /**
     * 新增【请填写功能名称】
     */
    Boolean insertByBo(BLabelBo bo);

    /**
     * 修改【请填写功能名称】
     */
    Boolean updateByBo(BLabelBo bo);

    /**
     * 校验并批量删除【请填写功能名称】信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
