package com.interview.system.service;

import com.interview.system.domain.BYuyue;
import com.interview.system.domain.vo.BYuyueVo;
import com.interview.system.domain.bo.BYuyueBo;
import com.interview.common.core.page.TableDataInfo;
import com.interview.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 预约Service接口
 *
 * @author interview
 * @date 2024-12-18
 */
public interface IBYuyueService {

    /**
     * 查询预约
     */
    BYuyueVo queryById(Long id);

    /**
     * 查询预约列表
     */
//    TableDataInfo<BYuyueVo> queryPageList(BYuyueBo bo, PageQuery pageQuery);
    List<BYuyueVo> queryPageList(BYuyueBo bo);

    /**
     * 查询预约列表
     */
    List<BYuyue> queryPageAllList(String date);

    /**
     * 查询预约列表
     */
    List<BYuyueVo> queryList(BYuyueBo bo);

    /**
     * 新增预约
     */
    Boolean insertByBo(BYuyueBo bo);

    /**
     * 修改预约
     */
    Boolean updateByBo(BYuyueBo bo);

    /**
     * 校验并批量删除预约信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
