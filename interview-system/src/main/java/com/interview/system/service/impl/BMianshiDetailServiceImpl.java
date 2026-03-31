package com.interview.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.interview.common.utils.StringUtils;
import com.interview.common.core.page.TableDataInfo;
import com.interview.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.interview.system.domain.BMianshi;
import com.interview.system.domain.BYuyue;
import com.interview.system.mapper.BMianshiMapper;
import com.interview.system.mapper.BYuyueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.interview.system.domain.bo.BMianshiDetailBo;
import com.interview.system.domain.vo.BMianshiDetailVo;
import com.interview.system.domain.BMianshiDetail;
import com.interview.system.mapper.BMianshiDetailMapper;
import com.interview.system.service.IBMianshiDetailService;

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
@Slf4j
public class BMianshiDetailServiceImpl implements IBMianshiDetailService {

    private final BMianshiDetailMapper baseMapper;
    private final BMianshiMapper mianshiMapper;
    private final BYuyueMapper yuyueMapper;

    /**
     * 查询【请填写功能名称】
     */
    @Override
    public BMianshiDetailVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public TableDataInfo<BMianshiDetailVo> queryPageList(BMianshiDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<BMianshiDetail> lqw = buildQueryWrapper(bo);
        Page<BMianshiDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @Override
    public List<BMianshiDetailVo> queryList(BMianshiDetailBo bo) {
        LambdaQueryWrapper<BMianshiDetail> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<BMianshiDetail> buildQueryWrapper(BMianshiDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<BMianshiDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getHumanUrl()), BMianshiDetail::getHumanUrl, bo.getHumanUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getHumanText()), BMianshiDetail::getHumanText, bo.getHumanText());
        lqw.eq(StringUtils.isNotBlank(bo.getMsUrl()), BMianshiDetail::getMsUrl, bo.getMsUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getMsText()), BMianshiDetail::getMsText, bo.getMsText());
        lqw.eq(StringUtils.isNotBlank(bo.getHumanDuration()), BMianshiDetail::getHumanDuration, bo.getHumanDuration());
        lqw.eq(StringUtils.isNotBlank(bo.getMsDuration()), BMianshiDetail::getMsDuration, bo.getMsDuration());
        lqw.eq(StringUtils.isNotBlank(bo.getAnswerText()), BMianshiDetail::getAnswerText, bo.getAnswerText());
        lqw.eq(bo.getScore() != null, BMianshiDetail::getScore, bo.getScore());
        lqw.eq(bo.getFlag() != null, BMianshiDetail::getFlag, bo.getFlag());
        lqw.eq(bo.getMsId() != null, BMianshiDetail::getMsId, bo.getMsId());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     */
    @Override
    public Boolean insertByBo(BMianshiDetailBo bo) {
        BMianshiDetail add = BeanUtil.toBean(bo, BMianshiDetail.class);
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
    public Boolean updateByBo(BMianshiDetailBo bo) {
        BMianshiDetail update = BeanUtil.toBean(bo, BMianshiDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BMianshiDetail entity){
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

    @EventListener
    @Async
    public String callDifyApi(BMianshi update) {
        log.info("进来了title" + update.getTitle());

        int retries = 0;
        while (retries < 3) {
            try {
                // 构建请求体
                JSONObject data = new JSONObject();
                JSONObject inputs = new JSONObject();
                inputs.set("interview", update.getMsContent());
                data.set("inputs", inputs);
                data.set("query", update.getTitle());
                data.set("response_mode", "blocking");
                data.set("user", "java-mianshiai2");
                // 发送 POST 请求
                HttpResponse response = HttpRequest.post("https://api.dify.ai/v1/chat-messages")
                    .header("Authorization", "Bearer app-**********************")
                    .header("Content-Type", "application/json")
                    .timeout(600000)
                    .body(data.toString())
                    .execute();
                // 检查响应状态
                log.info(response.body());
                if (response.isOk()) {
                    // 解析响应内容
                    JSONObject responseJson = new JSONObject(response.body());
//                    log.info
                    String answer = responseJson.getStr("answer");
                    update.setMsReport(answer);
                    mianshiMapper.updateById(update);
                    log.info("正确返回了" + answer);
                    BYuyue yuyue = yuyueMapper.selectOne(new LambdaQueryWrapper<BYuyue>().eq(BYuyue::getMsId, update.getId()).last("limit 1"));
                    if(ObjectUtil.isNotNull(yuyue)) {
                        yuyue.setMsStatus(4);
                        yuyueMapper.updateById(yuyue);
                    }
                    if(ObjectUtil.isNotNull(yuyue) && ObjectUtil.isNotEmpty(yuyue.getResumePostId())) {

                        String url = "http://localhost:8189/api/resume/ai/synchInterviewResults";

                        // 构造请求参数
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.set("resultJson", answer);
                        jsonObject.set("resumePostId", yuyue.getResumePostId());
                        jsonObject.set("msReportId", update.getId());
                        jsonObject.set("sign", "981448677271396e6a89ef1a6941f813");

                        // 发送POST请求
                        HttpResponse tongzhi = HttpRequest.post(url)
                            .body(JSONUtil.toJsonStr(jsonObject))
                            .execute();

                        // 处理响应
                        if (tongzhi.isOk()) {
                            String body = tongzhi.body();
                            log.info("通知响应内容: " + body);
                        } else {
                            log.info("通知请求失败，状态码: " + tongzhi.getStatus());
                        }
                    }
                    return answer;
                } else {
                    System.out.println("请求失败，状态码：" + response.getStatus());
                }
            } catch (Exception e) {
                System.out.println("请求出现异常：" + e.getMessage());
            }
            retries++;
            System.out.println("第 " + retries + " 次重试请求...");
        }
        return null;
    }
}
