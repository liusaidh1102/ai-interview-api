package com.interview.web.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.interview.common.annotation.Log;
import com.interview.common.annotation.RepeatSubmit;
import com.interview.common.core.controller.BaseController;
import com.interview.common.core.domain.PageQuery;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import com.interview.common.core.domain.R;
import com.interview.common.core.page.TableDataInfo;
import com.interview.common.core.validate.AddGroup;
import com.interview.common.core.validate.EditGroup;
import com.interview.common.enums.BusinessType;
import com.interview.common.helper.LoginHelper;
import com.interview.common.utils.poi.ExcelUtil;
import com.interview.common.utils.redis.RedisUtils;
import com.interview.system.domain.bo.*;
import com.interview.system.domain.vo.BLabelVo;
import com.interview.system.domain.vo.SysOssVo;
import com.interview.system.service.AiService;
import com.interview.system.service.RateLimiterService;
import com.interview.system.service.TtsTaskService;
import com.interview.system.service.IBLabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;

/**
 * ai
 *
 * @author interview
 * @date 2024-12-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/system/ai")
public class AiController extends BaseController {
    private final AiService aiService;
    private final RateLimiterService rateLimiterService;
    private final TtsTaskService ttsTaskService;

    /**
     * 语音合成：文字转换为.wav的语音
     */
//    @SaCheckPermission("system:label:add")
    @Log(title = "tts", businessType = BusinessType.INSERT)
    @PostMapping("tts")
    public R<Object> tts(@Validated(AddGroup.class) @RequestBody AiTtsBo text) {
        String userKey;
        try {
            userKey = "20231714422";
        } catch (Exception e) {
            userKey = "anonymous";
        }
        if (!rateLimiterService.allow(userKey)) {
            return R.fail("请求过于频繁，请稍后再试");
        }
        String taskId = text.getTaskId() != null ? text.getTaskId() : java.util.UUID.randomUUID().toString();
        ttsTaskService.submit(userKey, taskId, text.getText());
        return R.ok("accepted");
    }


    @Log(title = "asr", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping("/asr")
    public R<Object> asr(@RequestPart("file") MultipartFile file) {
        try {
            JSONObject result = aiService.asrProcess(file);
            return R.ok(result);
        } catch (Exception e) {
            log.error("ASR failed", e);
            return R.fail("语音识别失败");
        }
    }

    @Log(title = "bqfk", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping("/bqfk")
    public R<Object> bqfk(@RequestPart("file") MultipartFile file,
                          @RequestParam("msId") String msId,
                          @RequestParam("index") String index) {
        try {
            Future<JSONObject> future = aiService.expressionProcess(file, msId, index);
            JSONObject result = future.get();
            return R.ok(result);
        } catch (Exception e) {
            log.error("Expression analysis failed", e);
            return R.fail("表情分析失败");
        }
    }

    /**
     * 创建直播间
     */
    @Log(title = "创建直播间", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("zbj")
    public R<Object> zbj() {
        String response = HttpUtil.get("http://localhost:8181/create/zbj?username=" + LoginHelper.getUsername());
        JSONObject jsonstring = (JSONObject) JSONUtil.parse(response);
        return R.ok(jsonstring.get("message"));
    }

    /**
     * 创建直播间
     */
    @Log(title = "创建直播间", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("v2/zbj")
    public R<Object> zbjv2(@Validated(AddGroup.class) @RequestBody AiQuestionBo questionBo) {
        String response = HttpUtil.get("http://localhost:8181/create/zbjv2?username="
            + LoginHelper.getUsername()
            + "&job=" + questionBo.getJob()
            + "&count=" + questionBo.getCount()
        );
        JSONObject jsonstring = (JSONObject) JSONUtil.parse(response);
        JSONObject object = new JSONObject();
        object.set("zbjname", jsonstring.get("zbjname"));
        object.set("question", jsonstring.get("question"));
        return R.ok(object);
//        return R.ok(jsonstring.get("message"));
    }

//    /**
//     * 语音识别：将.wav音频上传到服务器，调用python脚本转换为文字返回。
//     * @param file .wav音频文件
//     * @return 文字内容,和文件url
//     */
//    @Log(title = "asr", businessType = BusinessType.INSERT)
//    @RepeatSubmit()
//    @PostMapping("asr")
//    public R<Object> asr(@RequestPart("file") MultipartFile file) {
//
//        // 检查上传的文件是否为空
//        if (file.isEmpty()) {
//            return R.fail("上传的文件为空");
//        }
//
//        // 指定文件保存的目录
//        String uploadDir = "/home/user/project/human_ms/audios/";
////        String uploadDir = "/home/sensoro/project/resume/dist/apps/server/audios/tests/";
////        String uploadDir = "/Users/xiaosang/Desktop/quick/aimianshi/interview/upload/";
//        // 创建文件保存目录对象
//        File dir = new File(uploadDir);
//        // 如果目录不存在，则创建目录
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        // 获取文件的原始扩展名
//        String originalFilename = file.getOriginalFilename();
//        String extension = "";
//        if (originalFilename != null && originalFilename.contains(".")) {
//            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        }
//
//        // 使用 UUID 生成新的文件名
//        String uuid = IdUtil.simpleUUID();
//        String newFileName = uuid + extension;
//        // 构建新的文件路径
//        String filePath = uploadDir + newFileName + ".wav";
//        File dest = new File(filePath);
//
//        try {
//            // 定义文件权限：rw-rw-r--
//            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-r--");
//            // 创建文件+指定权限
//            if (!Files.exists(dest.toPath())) {
//                Files.createFile(dest.toPath(), PosixFilePermissions.asFileAttribute(perms));
//            }
//            // 写入内容
//            Files.write(dest.toPath(), file.getBytes());
//            // 将上传的文件保存到指定路径，避免使用transferTo（权限问题）
//           //  file.transferTo(dest);
//        } catch (IOException e) {
//            // 若保存过程中出现异常，返回错误信息
//            e.printStackTrace();
//            return R.fail("文件上传失败：" + e.getMessage());
//        }
////        JSONObject jsonstring = new JSONObject();
//        String response = HttpUtil.get("http://localhost:8181:8181/asr?url=tests/" + newFileName + ".wav");
//        JSONObject jsonstring = (JSONObject) JSONUtil.parse(response);
//        jsonstring.set("url", newFileName + ".wav");
//
//        return R.ok(jsonstring);
//    }
//
//    /**
//     * 表情识别
//     */
//    @Log(title = "bqfk", businessType = BusinessType.INSERT)
//    @RepeatSubmit()
//    @PostMapping("bqfk")
//    public R<Object> bqfk(@RequestPart("file") MultipartFile file, @RequestParam("msId") String msId,
//                          @RequestParam("index") String index) {
//        // 检查上传的文件是否为空
//        if (file.isEmpty()) {
//            return R.fail("上传的文件为空");
//        }
//        // 指定文件保存的目录
////        String uploadDir = "/home/sensoro/project/resume/dist/apps/server/audios/tests/";
//        String uploadDir = "/home/user/project/human_ms/audios/";
////        String uploadDir = "/Users/xiaosang/Desktop/quick/aimianshi/interview/upload/";
//        // 创建文件保存目录对象
//        File dir = new File(uploadDir);
//        // 如果目录不存在，则创建目录
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//
//        // 获取文件的原始扩展名
//        String originalFilename = file.getOriginalFilename();
//        String extension = "";
//        if (originalFilename != null && originalFilename.contains(".")) {
//            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        }
//
//        // 使用 UUID 生成新的文件名
//        String uuid = IdUtil.simpleUUID();
//        String newFileName = uuid + extension + ".mp4";
//        // 构建新的文件路径
//        String filePath = uploadDir + newFileName;
//        File dest = new File(filePath);
//        log.info("文件上传成功，保存路径：" + filePath);
//        try {
//            // 定义文件权限：rw-rw-r--
//            Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-rw-r--");
//            // 创建文件+指定权限
//            if (!Files.exists(dest.toPath())) {
//                Files.createFile(dest.toPath(), PosixFilePermissions.asFileAttribute(perms));
//            }
//            // 写入内容
//            Files.write(dest.toPath(), file.getBytes());
//            // 将上传的文件保存到指定路径
////            file.transferTo(dest);
//            log.info("try catch 文件上传成功，保存路径：" + filePath);
//        } catch (IOException e) {
//            // 若保存过程中出现异常，返回错误信息
//            e.printStackTrace();
//            return R.fail("文件上传失败：" + e.getMessage());
//        }
//        String response = HttpUtil.get("http://localhost:8181/bqfk?msId= " + msId + "&index=" + index + "&url=" + newFileName);
////        JSONObject jsonstring = (JSONObject) JSONUtil.parse(response);
//        JSONObject jsonstring = new JSONObject();
//        jsonstring.set("url", newFileName);
//        jsonstring.set("msId", msId);
//        jsonstring.set("index", index);
//
//        return R.ok(jsonstring);
//    }


    /**
     * 数字人驱动
     */
    @Log(title = "数字人驱动", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("human")
    public R<Object> human(@Validated(AddGroup.class) @RequestBody AiHumanBo human) {
        if(!RedisUtils.isExistsObject(human.getZbjname() + "check")) {
            return R.fail("直播间已销毁");
        }
        String response = HttpUtil.get("http://localhost:8181/human?zbjname=" + human.getZbjname() + "&audioUrl=" + human.getAudioUrl());
        JSONObject jsonstring = (JSONObject) JSONUtil.parse(response);
        return R.ok(jsonstring.get("message"));
    }

    /**
     * 数字人驱动
     */
    @Log(title = "数字人驱动", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("v2/human")
    public R<Object> humanv2(@Validated(AddGroup.class) @RequestBody AiHumanBo human) {
        if(!RedisUtils.isExistsObject(human.getZbjname() + "check")) {
            return R.fail("直播间已销毁");
        }
        String response = HttpUtil.get("http://localhost:8181/humanv2?zbjname=" + human.getZbjname() + "&audioUrl=" + human.getAudioUrl());
        JSONObject jsonstring = (JSONObject) JSONUtil.parse(response);
        return R.ok(jsonstring.get("message"));
    }

//    /**
//     * 修改【请填写功能名称】
//     */
//    @SaCheckPermission("system:label:edit")
//    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
//    @RepeatSubmit()
//    @PutMapping()
//    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BLabelBo bo) {
//        return toAjax(iBLabelService.updateByBo(bo));
//    }
//
//    /**
//     * 删除【请填写功能名称】
//     *
//     * @param ids 主键串
//     */
//    @SaCheckPermission("system:label:remove")
//    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
//    public R<Void> remove(@NotEmpty(message = "主键不能为空")
//                          @PathVariable Long[] ids) {
//        return toAjax(iBLabelService.deleteWithValidByIds(Arrays.asList(ids), true));
//    }
}
