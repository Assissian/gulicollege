package com.atcwl.gulicollege.api;

import com.atcwl.gulicollege.api.fallback.AliyunDegradeApiImpl;
import com.atcwl.gulicollege.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@FeignClient(name = "service-aliyun", fallback = AliyunDegradeApiImpl.class)
@Component
public interface AliyunApiClient{
    @DeleteMapping("/gulicollege/vod-service/remove-video")
    Result<String> removeVideo(@RequestParam("videoIds") List<String> videoIds);
}
