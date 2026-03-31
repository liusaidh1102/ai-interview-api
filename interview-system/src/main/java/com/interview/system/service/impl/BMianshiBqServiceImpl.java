package com.interview.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.interview.common.utils.StringUtils;
import com.interview.common.core.page.TableDataInfo;
import com.interview.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.interview.system.domain.vo.BMianshiVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.interview.system.domain.bo.BMianshiBqBo;
import com.interview.system.domain.vo.BMianshiBqVo;
import com.interview.system.domain.BMianshiBq;
import com.interview.system.mapper.BMianshiBqMapper;
import com.interview.system.service.IBMianshiBqService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author interview
 * @date 2025-03-12
 */
@RequiredArgsConstructor
@Service
public class BMianshiBqServiceImpl implements IBMianshiBqService {

    private final BMianshiBqMapper baseMapper;

    /**
     * 查询【请填写功能名称】
     */
    @Override
    public BMianshiBqVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<BMianshiBqVo> queryPageList(BMianshiBqBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BMianshiBq> lqw = buildQueryWrapper(bo);
        Page<BMianshiBqVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<BMianshiBqVo> queryList(BMianshiBqBo bo) {
        LambdaQueryWrapper<BMianshiBq> lqw = buildQueryWrapper(bo);
        List<BMianshiBqVo> mianshiBqVoList = baseMapper.selectVoList(lqw);
        List<BMianshiBqVo> mianshiBqVoListTemp =  new ArrayList<>();
        for (BMianshiBqVo mianshiVo : mianshiBqVoList) {
            boolean flag = false;
            for(BMianshiBqVo mianshiVoTemp : mianshiBqVoListTemp) {
                if(mianshiVo.getIndex().equals(mianshiVoTemp.getIndex())) {
                    flag = true;
                    if(mianshiVo.getSort() < mianshiVoTemp.getSort()) {
                        mianshiVoTemp.setId(mianshiVo.getId());
                        mianshiVoTemp.setContent(mianshiVo.getContent());
                        mianshiVoTemp.setIndex(mianshiVo.getIndex());
                        mianshiVoTemp.setMsId(mianshiVo.getMsId());
                        mianshiVoTemp.setSort(mianshiVo.getSort());
                        mianshiVoTemp.setUrl(mianshiVo.getUrl());
                    }
                }
            }
            if(!flag) {
                mianshiBqVoListTemp.add(mianshiVo);
            }
        }
        return mianshiBqVoListTemp;
    }

    private LambdaQueryWrapper<BMianshiBq> buildQueryWrapper(BMianshiBqBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BMianshiBq> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getMsId() != null, BMianshiBq::getMsId, bo.getMsId());
        lqw.eq(bo.getIndex() != null, BMianshiBq::getIndex, bo.getIndex());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), BMianshiBq::getContent, bo.getContent());
        lqw.eq(bo.getSort() != null, BMianshiBq::getSort, bo.getSort());
        lqw.eq(StringUtils.isNotBlank(bo.getUrl()), BMianshiBq::getUrl, bo.getUrl());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    public Boolean insertByBo(BMianshiBqBo bo) {
        BMianshiBq add = BeanUtil.toBean(bo, BMianshiBq.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改【请填写功能名称】
     */
    @Override
    public Boolean updateByBo(BMianshiBqBo bo) {
        BMianshiBq update = BeanUtil.toBean(bo, BMianshiBq.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BMianshiBq entity){
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
