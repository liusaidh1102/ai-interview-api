package com.interview.system.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.interview.system.domain.bo.AiTtsBo;
import org.springframework.web.multipart.MultipartFile;
import java.util.concurrent.Future;


public interface AiService {




    public JSONObject asrProcess(MultipartFile file) ;

    public Future<JSONObject> expressionProcess(MultipartFile file, String msId, String index);

}
