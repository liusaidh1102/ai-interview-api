package com.interview.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.interview.common.utils.StringUtils;
import com.interview.common.core.page.TableDataInfo;
import com.interview.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.interview.system.domain.BLabel;
import com.interview.system.domain.vo.BLabelVo;
import com.interview.system.mapper.BLabelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.interview.system.domain.bo.BJobsBo;
import com.interview.system.domain.vo.BJobsVo;
import com.interview.system.domain.BJobs;
import com.interview.system.mapper.BJobsMapper;
import com.interview.system.service.IBJobsService;

import java.util.*;

/**
 * 岗位Service业务层处理
 *
 * @author interview
 * @date 2024-12-02
 */
@RequiredArgsConstructor
@Service
public class BJobsServiceImpl implements IBJobsService {

    private final BJobsMapper baseMapper;
    private final BLabelMapper labelMapper;

    /**
     * 查询【请填写功能名称】
     */
    @Override
    public BJobsVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<BJobsVo> queryPageList(BJobsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BJobs> lqw = buildQueryWrapper(bo);
        Page<BJobsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<BJobsVo> queryList(BJobsBo bo) {
        LambdaQueryWrapper<BJobs> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<BLabelVo> queryAllList(BJobsBo bo) {
        LambdaQueryWrapper<BJobs> lqw = buildQueryWrapper(bo);
        List<BJobsVo> jobsVoList = baseMapper.selectVoList(lqw);
        LambdaQueryWrapper<BLabel> lqw2 = Wrappers.lambdaQuery();
        List<BLabelVo> labelVoList = labelMapper.selectVoList(lqw2.orderByDesc(BLabel::getFlag));
        BLabelVo remenLabel = new BLabelVo();
        remenLabel.setType("热门");
        remenLabel.setJobs(new ArrayList<>());
        labelVoList.add(remenLabel);
        for(BJobsVo jobs : jobsVoList) {
            if(jobs.getIsRemen().equals(1L)) {
                remenLabel.getJobs().add(jobs);
            }
            for(BLabelVo labelVo : labelVoList) {
                if(jobs.getTypeId().equals(labelVo.getId())) {
                    if(ObjectUtil.isNull(labelVo.getJobs())) {
                        labelVo.setJobs(new ArrayList<>());
                    }
                    labelVo.getJobs().add(jobs);
                    break;
                }
            }
        }
        // 使用 Collections.reverse() 方法翻转列表
        Collections.reverse(labelVoList);

        return labelVoList;
    }

    private LambdaQueryWrapper<BJobs> buildQueryWrapper(BJobsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BJobs> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), BJobs::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhoto()), BJobs::getPhoto, bo.getPhoto());
        lqw.eq(StringUtils.isNotBlank(bo.getMsg()), BJobs::getMsg, bo.getMsg());
        lqw.eq(bo.getTypeId() != null, BJobs::getTypeId, bo.getTypeId());
        lqw.eq(bo.getIsRemen() != null, BJobs::getIsRemen, bo.getIsRemen());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    public Boolean insertByBo(BJobsBo bo) {
        BJobs add = BeanUtil.toBean(bo, BJobs.class);
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
    public Boolean updateByBo(BJobsBo bo) {
        BJobs update = BeanUtil.toBean(bo, BJobs.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BJobs entity){
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
