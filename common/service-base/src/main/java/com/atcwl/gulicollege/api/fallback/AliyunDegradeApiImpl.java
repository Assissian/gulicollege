package com.atcwl.gulicollege.api.fallback;

import com.atcwl.gulicollege.api.AliyunApiClient;
import com.atcwl.gulicollege.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Component
public class AliyunDegradeApiImpl implements AliyunApiClient {
    @Override
    public Result<String> removeVideo(List<String> videoIds) {
        return Result.build(30001, "包抱歉，当前服务不可用");
    }
}
