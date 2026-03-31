package com.interview.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.interview.common.core.domain.entity.SysUser;
import com.interview.common.exception.ServiceException;
import com.interview.common.helper.LoginHelper;
import com.interview.common.utils.DateUtils;
import com.interview.common.utils.StringUtils;
import com.interview.common.core.page.TableDataInfo;
import com.interview.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.interview.system.domain.BJianli;
import com.interview.system.domain.BJobs;
import com.interview.system.domain.vo.BJianliVo;
import com.interview.system.domain.vo.BJobsVo;
import com.interview.system.mapper.BJianliMapper;
import com.interview.system.mapper.BJobsMapper;
import com.interview.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.interview.system.domain.bo.BYuyueBo;
import com.interview.system.domain.vo.BYuyueVo;
import com.interview.system.domain.BYuyue;
import com.interview.system.mapper.BYuyueMapper;
import com.interview.system.service.IBYuyueService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 预约Service业务层处理
 *
 * @author interview
 * @date 2024-12-18
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class BYuyueServiceImpl implements IBYuyueService {

    private final BYuyueMapper baseMapper;
    private final BJianliMapper jianliMapper;
    private final BJobsMapper jobsMapper;
    private final SysUserMapper userMapper;


    /**
     * 查询预约
     */
    @Override
    public BYuyueVo queryById(Long id){
        BYuyueVo yuyueVo = baseMapper.selectVoById(id);
        if(ObjectUtil.isNotEmpty(yuyueVo.getResumePostId())) {
            String url = "http://localhost:8189/api/resume/ai/getResumeAndPost";

            // 构造请求参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("resumePostId", yuyueVo.getResumePostId());
            jsonObject.set("sign", "981448677271396e6a89ef1a6941f813");

            // 发送POST请求
            HttpResponse response = HttpRequest.post(url)
                .body(JSONUtil.toJsonStr(jsonObject))
                .execute();

            // 处理响应
            if (response.isOk()) {
                String body = response.body();
                // 解析响应内容
                JSONObject responseJson = new JSONObject(body);
//                "content": "string",
//                    "msg": "string",
//                    "name": "string"
                responseJson = new JSONObject(responseJson.getStr("data"));
                String content = responseJson.getStr("content");
                String msg = responseJson.getStr("msg");
                String name = responseJson.getStr("name");
                BJianli jianli = new BJianli();
                BJobs jobs = new BJobs();
                jianli.setContent(content);
                jobs.setMsg(msg);
                jobs.setName(name);
                yuyueVo.setJianli(jianli);
                yuyueVo.setJobs(jobs);
                log.info("响应内容: " + body);
            } else {
                log.info("请求失败，状态码: " + response.getStatus());
            }
        } else {
            if (ObjectUtil.isNotEmpty(yuyueVo.getJianliId()) && !yuyueVo.getJianliId().equals(0L)) {
                yuyueVo.setJianli(jianliMapper.selectById(yuyueVo.getJianliId()));
            }
            if (ObjectUtil.isNotEmpty(yuyueVo.getJobsId()) && !yuyueVo.getJobsId().equals(0L)) {
                yuyueVo.setJobs(jobsMapper.selectById(yuyueVo.getJobsId()));
            }
        }
        return yuyueVo;
    }

    /**
     * 查询预约列表
     */
//    @Override
    public TableDataInfo<BYuyueVo> queryAdminPageList(BYuyueBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BYuyue> lqw = buildQueryWrapper(bo);
        lqw.orderByDesc(BYuyue::getYyTime);
        Page<BYuyueVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<BYuyueVo> yuyueVoList = result.getRecords();
        for (BYuyueVo yuyue : yuyueVoList) {
            if(!yuyue.getCreateBy().equals(LoginHelper.getUsername())) {
                yuyue.setCreateBy(null);
            }
            if (ObjectUtil.isNotEmpty(yuyue.getJianliId()) && !yuyue.getJianliId().equals(0L)) {
                yuyue.setJianli(jianliMapper.selectById(yuyue.getJianliId()));
            }
            if (ObjectUtil.isNotEmpty(yuyue.getJobsId()) && !yuyue.getJobsId().equals(0L)) {
                yuyue.setJobs(jobsMapper.selectById(yuyue.getJobsId()));
            }
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询预约列表
     */
    @Override
    public List<BYuyueVo> queryPageList(BYuyueBo bo) {
        LambdaQueryWrapper<BYuyue> lqw = buildQueryWrapper(bo);
        lqw.eq(BYuyue::getMsId, 0);
        lqw.eq(BYuyue::getUnionUserId, LoginHelper.getUsername());
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 计算 24 小时后的时间
        LocalDateTime twentyFourHoursLater = now.minusHours(24);

        lqw.gt(BYuyue::getYyTime, twentyFourHoursLater);
        lqw.orderByDesc(BYuyue::getYyTime);
        List<BYuyueVo> yuyueVoList = baseMapper.selectVoList(lqw);
//        List<BYuyueVo> yuyueVoList = result.getRecords();
        for (BYuyueVo yuyue : yuyueVoList) {
//            if(!yuyue.getCreateBy().equals(LoginHelper.getUsername())) {
//                yuyue.setCreateBy(null);
//            }
            if (ObjectUtil.isNotEmpty(yuyue.getJianliId()) && !yuyue.getJianliId().equals(0L)) {
                yuyue.setJianli(jianliMapper.selectById(yuyue.getJianliId()));
            }
            if (ObjectUtil.isNotEmpty(yuyue.getJobsId()) && !yuyue.getJobsId().equals(0L)) {
                yuyue.setJobs(jobsMapper.selectById(yuyue.getJobsId()));
            }
        }
        return yuyueVoList;
    }
    /**
     * 查询预约列表
     */
    @Override
    public List<BYuyue> queryPageAllList(String date) {
        LocalDate firstDay, lastDay;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            firstDay = LocalDate.parse(date + "-01", formatter).withDayOfMonth(1);
            lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());
        } catch (Exception e) {
            throw new ServiceException("日期格式错误");
        }


        // 假设YourEntityMapper是你对应实体的MyBatis-Plus的Mapper接口
        LambdaQueryWrapper<BYuyue> lqw = new LambdaQueryWrapper<>();

        lqw.ge(BYuyue::getYyTime, firstDay).le(BYuyue::getYyTime, lastDay);

        List<BYuyue> yuyueVoList = baseMapper.selectList(lqw);
//        List<BYuyueVo> yuyueVoList = result.getRecords();
        for (BYuyue yuyue : yuyueVoList) {
            if(!yuyue.getCreateBy().equals(LoginHelper.getUsername())) {
                yuyue.setCreateBy(null);
            }
        }
        return yuyueVoList;
    }

    /**
     * 查询预约列表
     */
    @Override
    public List<BYuyueVo> queryList(BYuyueBo bo) {
        LambdaQueryWrapper<BYuyue> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BYuyue> buildQueryWrapper(BYuyueBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BYuyue> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getYyTime() != null, BYuyue::getYyTime, bo.getYyTime());
        return lqw;
    }

    /**
     * 新增预约
     */
    @Override
    public Boolean insertByBo(BYuyueBo bo) {

        BYuyue add = BeanUtil.toBean(bo, BYuyue.class);
        add.setMsId(0L);
        add.setQiyeId(0L);
        add.setQiyeName("");
        SysUser user = userMapper.selectById(LoginHelper.getUserId());
        add.setUserFullname(user.getNickName());
        add.setMsStatus(1);
        add.setMsType(2);
        if(ObjectUtil.isEmpty(add.getJobsId()) && ObjectUtil.isEmpty(add.getJianliId())) {
            throw new ServiceException("必须选择岗位或者简历");
        }
        if(ObjectUtil.isNotEmpty(add.getJobsId())) {
            BJobsVo jobsVo = jobsMapper.selectVoById(add.getJobsId());
            add.setJobsTitle(jobsVo.getName());
            add.setMsTitle(jobsVo.getName());
        } else if(ObjectUtil.isNotEmpty(add.getJianliId())) {
            BJianliVo jianliVo = jianliMapper.selectVoById(add.getJianliId());
            add.setMsTitle(jianliVo.getName());
        }
        add.setUnionUserId(LoginHelper.getUsername());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改预约
     */
    @Override
    public Boolean updateByBo(BYuyueBo bo) {
        BYuyue update = BeanUtil.toBean(bo, BYuyue.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BYuyue entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除预约
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
