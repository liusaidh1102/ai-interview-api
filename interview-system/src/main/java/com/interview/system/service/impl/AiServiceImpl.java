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
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AiServiceImpl implements AiService {


    public String uploadDir = "/home/user/project/human_ms/audios/";
    public String baseUrl = "http://localhost:8181";



    // 引入 Redisson 客户端
    private final RedissonClient redissonClient = RedisUtils.getClient();


    public String saveFile(MultipartFile file, String prefix) {
        if (file.isEmpty()) {
            log.warn("文件上传失败：空文件，prefix={}", prefix);
            throw new RuntimeException("上传的文件不能为空"); // 自定义全局异常
        }

        String suffix = "";

        if(prefix.equals("bqfk")){
            suffix = ".mp4";
        }else if (prefix.equals("asr")) {
            suffix = ".wav";
        }

        // 生成唯一文件名
        String newFileName = IdUtil.fastSimpleUUID() + suffix;

        // 文件写入对应的目录
        String uploadDir = "/home/user/project/human_ms/audios/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dest = new File(uploadDir + newFileName);
        try {
            Files.write(dest.toPath(), file.getBytes());
        } catch (IOException e) {
            log.error("文件存储失败，filename: {}", newFileName, e);
            throw new RuntimeException("文件系统异常，保存失败");
        }
        return newFileName;


    }


    @Override
    public JSONObject asrProcess(MultipartFile file) {
        long startTime = System.currentTimeMillis();
        // 1. 保存文件
        String fileName = saveFile(file, "asr");

        // 2. 调用 Python 接口
        String url = baseUrl + "/asr?url=tests/" + fileName;
        try {
            log.info("ASR请求开始: fileName={}", fileName);
            String response = HttpUtil.get(url, 10000);

            JSONObject result = JSONUtil.parseObj(response);
            result.set("url", fileName);
            log.info("ASR请求成功: fileName={}, costMs={}", fileName, System.currentTimeMillis() - startTime);
            return result;
        } catch (RuntimeException e) {
            log.error("ASR请求失败: fileName={}, costMs={}", fileName, System.currentTimeMillis() - startTime, e);
            throw e;
        }
    }

    @Override
    @Async
    public Future<JSONObject> expressionProcess(MultipartFile file, String msId, String index) {
        long startTime = System.currentTimeMillis();
        try {
            String fileName = saveFile(file, "bqfk");

            String url = baseUrl + "/bqfk?msId=" + msId + "&index=" + index + "&url=" + fileName;
            String response = HttpUtil.get(url, 10000);

            JSONObject result = new JSONObject();
            result.set("url", fileName);
            result.set("msId", msId);
            result.set("index", index);
            log.info("表情分析请求成功: msId={}, index={}, fileName={}, costMs={}", msId, index, fileName, System.currentTimeMillis() - startTime);
            return new AsyncResult<>(result);
        } catch (Exception e) {
            log.error("表情分析请求失败: msId={}, index={}, costMs={}", msId, index, System.currentTimeMillis() - startTime, e);
            return new AsyncResult<>(new JSONObject().set("error", "Expression processing failed"));
        }
    }
}
