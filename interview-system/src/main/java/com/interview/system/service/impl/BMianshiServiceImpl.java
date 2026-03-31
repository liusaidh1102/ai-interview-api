package com.interview.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import com.interview.common.core.domain.entity.SysUser;
import com.interview.common.exception.ServiceException;
import com.interview.common.helper.LoginHelper;
import com.interview.common.utils.StringUtils;
import com.interview.common.core.page.TableDataInfo;
import com.interview.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.interview.system.domain.BYuyue;
import com.interview.system.domain.vo.BJianliVo;
import com.interview.system.domain.vo.BJobsVo;
import com.interview.system.domain.vo.BYuyueVo;
import com.interview.system.mapper.*;
import com.interview.system.service.IBMianshiDetailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.interview.system.domain.bo.BMianshiBo;
import com.interview.system.domain.vo.BMianshiVo;
import com.interview.system.domain.BMianshi;
import com.interview.system.service.IBMianshiService;

import java.util.Date;
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
public class BMianshiServiceImpl implements IBMianshiService {

    private static final Logger log = LoggerFactory.getLogger(BMianshiServiceImpl.class);
    private final BMianshiMapper baseMapper;
    private final BJianliMapper jianliMapper;
    private final BJobsMapper jobsMapper;
    private final BYuyueMapper yuyueMapper;
    private final SysUserMapper userMapper;
    private final IBMianshiDetailService detailService;

    /**
     * 查询【请填写功能名称】
     */
    @Override
    public BMianshiVo queryById(Long id){
        BMianshiVo mianshi =  baseMapper.selectVoById(id);
        LambdaQueryWrapper<BYuyue> yuYueLambdaQueryWrapper = new LambdaQueryWrapper<>();
        yuYueLambdaQueryWrapper.eq(BYuyue::getMsId, mianshi.getId()).last("limit 1");
        BYuyueVo yuyueVo = yuyueMapper.selectVoOne(yuYueLambdaQueryWrapper);
        mianshi.setYuyue(yuyueVo);
        //判断是上传简历/直接选岗位，简历信息/岗位信息 设置 JianliVo/JobsVo的相关信息
        if(!mianshi.getJianliId().equals(0L)) {
            mianshi.setJianliVo(jianliMapper.selectVoById(mianshi.getJianliId()));
        } else if (!mianshi.getJobsId().equals(0L)) {
            mianshi.setJobsVo(jobsMapper.selectVoById(mianshi.getJobsId()));
        }
        return mianshi;
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<BMianshiVo> queryMyPageList(BMianshiBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BMianshi> lqw = buildQueryWrapper(bo);
        lqw.eq(BMianshi::getCreateBy, LoginHelper.getUsername()).orderByDesc(BMianshi::getCreateTime);
        Page<BMianshiVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<BMianshiVo> bMianshiVoList = result.getRecords();
        for(BMianshiVo mianshi:bMianshiVoList) {
            LambdaQueryWrapper<BYuyue> yuYueLambdaQueryWrapper = new LambdaQueryWrapper<>();
            yuYueLambdaQueryWrapper.eq(BYuyue::getMsId, mianshi.getId()).last("limit 1");
            mianshi.setMsContent(null);
            mianshi.setMsReport(null);
            BYuyueVo yuyueVo = yuyueMapper.selectVoOne(yuYueLambdaQueryWrapper);
            if(ObjectUtil.isNotEmpty(yuyueVo)) {
                mianshi.setYuyue(yuyueVo);
            }
//            if(!mianshi.getJianliId().equals(0L)) {
//                mianshi.setJianliVo(jianliMapper.selectVoById(mianshi.getJianliId()));
//            } else if (!mianshi.getJobsId().equals(0L)) {
//                mianshi.setJobsVo(jobsMapper.selectVoById(mianshi.getJobsId()));
//            }
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<BMianshiVo> queryPageList(BMianshiBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BMianshi> lqw = buildQueryWrapper(bo);
        Page<BMianshiVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<BMianshiVo> queryList(BMianshiBo bo) {
        LambdaQueryWrapper<BMianshi> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BMianshi> buildQueryWrapper(BMianshiBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BMianshi> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), BMianshi::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getMsUrl()), BMianshi::getMsUrl, bo.getMsUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getMsContent()), BMianshi::getMsContent, bo.getMsContent());
        lqw.eq(bo.getJobsId() != null, BMianshi::getJobsId, bo.getJobsId());
        lqw.eq(bo.getJianliId() != null, BMianshi::getJianliId, bo.getJianliId());
        lqw.eq(StringUtils.isNotBlank(bo.getMsBiaoqing()), BMianshi::getMsBiaoqing, bo.getMsBiaoqing());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    public BMianshi insertByBo(BMianshiBo bo) {
        BMianshi add = BeanUtil.toBean(bo, BMianshi.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            if(ObjectUtil.isNotEmpty(bo.getYuyueId())) {
                BYuyue yuyue = yuyueMapper.selectById(bo.getYuyueId());
                yuyue.setMsId(add.getId());
                yuyue.setMsTitle(add.getTitle());
                yuyue.setMsStatus(2);
                yuyueMapper.updateById(yuyue);
            } else {
                BYuyue addYue = new BYuyue();
                addYue.setMsId(add.getId());
                SysUser user = userMapper.selectById(LoginHelper.getUserId());
                log.info("面试用户：{}", user);
                addYue.setUserFullname(user.getNickName());
                addYue.setMsStatus(2);
                addYue.setMsType(1);
                addYue.setJianliId(add.getJianliId());
                addYue.setJobsId(add.getJobsId());
                if(ObjectUtil.isNotEmpty(add.getJobsId())) {
                    BJobsVo jobsVo = jobsMapper.selectVoById(add.getJobsId());
                    addYue.setJobsTitle(jobsVo.getName());
                }
                log.info("设置用户名LoginHelper.getUserName：{}", LoginHelper.getUsername());
                addYue.setUnionUserId(LoginHelper.getUsername());
                yuyueMapper.insert(addYue);
            }
            bo.setId(add.getId());
        }
        return add;
    }

    /**
     * 修改面试【同时调用dify异步生成面试报告】
     */
    @Override
    public Boolean updateByBo(BMianshiBo bo) {
        BMianshi update = baseMapper.selectById(bo.getId());

        BYuyue yuyue = yuyueMapper.selectOne(new LambdaQueryWrapper<BYuyue>().eq(BYuyue::getMsId, bo.getId()).last("limit 1"));
        if(ObjectUtil.isNull(yuyue)) {
                BYuyue addYue = new BYuyue();
                addYue.setMsId(update.getId());
                SysUser user = userMapper.selectById(LoginHelper.getUserId());
                addYue.setUserFullname(user.getNickName());
                addYue.setMsStatus(3);
                addYue.setMsType(1);
                addYue.setJianliId(update.getJianliId());
                addYue.setJobsId(update.getJobsId());
                if(ObjectUtil.isNotEmpty(update.getJobsId())) {
                    BJobsVo jobsVo = jobsMapper.selectVoById(update.getJobsId());
                    addYue.setJobsTitle(jobsVo.getName());
                }
                addYue.setUnionUserId(LoginHelper.getUsername());
                yuyueMapper.insert(addYue);
        } else {
            yuyue.setMsStatus(3);
            yuyueMapper.updateById(yuyue);
        }
        update.setMsContent(bo.getMsContent());
//        yuyueMapper.se
        update.setEndTime(new Date());
        validEntityBeforeSave(update);
        // 生成面试报告
        detailService.callDifyApi(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 修改【请填写功能名称】
     */
    @Override
    public Boolean bgsc(Long id) {
        BMianshi update = baseMapper.selectById(id);
        if (ObjectUtil.isNull(update)) {
            return false;
        }

        BYuyue yuyue = yuyueMapper.selectOne(new LambdaQueryWrapper<BYuyue>().eq(BYuyue::getMsId, id).last("limit 1"));
        if(ObjectUtil.isNull(yuyue)) {
            BYuyue addYue = new BYuyue();
            addYue.setMsId(update.getId());
            SysUser user = userMapper.selectById(LoginHelper.getUserId());
            addYue.setUserFullname(user.getNickName());
            addYue.setMsStatus(3);
            addYue.setMsType(1);
            addYue.setJianliId(update.getJianliId());
            addYue.setJobsId(update.getJobsId());
            if(ObjectUtil.isNotEmpty(update.getJobsId())) {
                BJobsVo jobsVo = jobsMapper.selectVoById(update.getJobsId());
                addYue.setJobsTitle(jobsVo.getName());
            }
            addYue.setUnionUserId(LoginHelper.getUsername());
            yuyueMapper.insert(addYue);
        } else {
            yuyue.setMsStatus(3);
            yuyueMapper.updateById(yuyue);
        }
        detailService.callDifyApi(update);
        return baseMapper.updateById(update) > 0;
    }



    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BMianshi entity){
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
