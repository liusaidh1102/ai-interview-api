package com.interview.system.service.impl;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.executor.RedissonLockExecutor;
import com.interview.common.annotation.Log;
import com.interview.common.annotation.RepeatSubmit;
import com.interview.common.core.domain.R;
import com.interview.common.core.validate.AddGroup;
import com.interview.common.enums.BusinessType;
import com.interview.common.utils.redis.RedisUtils;
import com.interview.system.domain.bo.AiTtsBo;
import com.interview.system.service.AiService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.interview.system.service.AiService;
import com.interview.system.service.IInterviewTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final IInterviewTaskService interviewTaskService;

    @Override
    public JSONObject asrProcess(MultipartFile file) {
        String userKey = "anonymous";
        Map<String, Object> result = interviewTaskService.submitAsr(userKey, file);
        return JSONUtil.parseObj(result);
    }

    @Override
    @Async
    public Future<JSONObject> expressionProcess(MultipartFile file, String msId, String index) {
        String userKey = "anonymous";
        Map<String, Object> result = interviewTaskService.submitBqfk(userKey, file, msId, index);
        return new AsyncResult<>(JSONUtil.parseObj(result));
    }
}
