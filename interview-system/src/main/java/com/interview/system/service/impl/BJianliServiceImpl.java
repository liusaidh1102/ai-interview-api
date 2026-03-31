package com.interview.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.interview.common.utils.StringUtils;
import com.interview.common.core.page.TableDataInfo;
import com.interview.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.interview.system.domain.bo.BJianliBo;
import com.interview.system.domain.vo.BJianliVo;
import com.interview.system.domain.BJianli;
import com.interview.system.mapper.BJianliMapper;
import com.interview.system.service.IBJianliService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author interview
 * @date 2024-12-02
 */
@RequiredArgsConstructor
@Service
public class BJianliServiceImpl implements IBJianliService {

    private final BJianliMapper baseMapper;

    /**
     * 查询【请填写功能名称】
     */
    @Override
    public BJianliVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<BJianliVo> queryPageList(BJianliBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BJianli> lqw = buildQueryWrapper(bo);
        Page<BJianliVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<BJianliVo> queryList(BJianliBo bo) {
        LambdaQueryWrapper<BJianli> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BJianli> buildQueryWrapper(BJianliBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BJianli> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getUrl()), BJianli::getUrl, bo.getUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), BJianli::getContent, bo.getContent());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    public BJianli insertByBo(BJianliBo bo) {
        BJianli add = BeanUtil.toBean(bo, BJianli.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return add;
    }

    /**
     * 修改【请填写功能名称】
     */
    @Override
    public Boolean updateByBo(BJianliBo bo) {
        BJianli update = BeanUtil.toBean(bo, BJianli.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BJianli entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除【请填写功能名称】
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
