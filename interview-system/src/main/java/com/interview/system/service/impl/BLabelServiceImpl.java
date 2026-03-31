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
import com.interview.system.domain.bo.BLabelBo;
import com.interview.system.domain.vo.BLabelVo;
import com.interview.system.domain.BLabel;
import com.interview.system.mapper.BLabelMapper;
import com.interview.system.service.IBLabelService;

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
public class BLabelServiceImpl implements IBLabelService {

    private final BLabelMapper baseMapper;

    /**
     * 查询【请填写功能名称】
     */
    @Override
    public BLabelVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<BLabelVo> queryPageList(BLabelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BLabel> lqw = buildQueryWrapper(bo);
        Page<BLabelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<BLabelVo> queryList(BLabelBo bo) {
        LambdaQueryWrapper<BLabel> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BLabel> buildQueryWrapper(BLabelBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BLabel> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getType()), BLabel::getType, bo.getType());
        lqw.eq(bo.getFlag() != null, BLabel::getFlag, bo.getFlag());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    public Boolean insertByBo(BLabelBo bo) {
        BLabel add = BeanUtil.toBean(bo, BLabel.class);
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
    public Boolean updateByBo(BLabelBo bo) {
        BLabel update = BeanUtil.toBean(bo, BLabel.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BLabel entity){
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
