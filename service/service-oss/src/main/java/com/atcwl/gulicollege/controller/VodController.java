package com.atcwl.gulicollege.controller;

import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Api(tags = "视频点播")
@RestController
@RequestMapping("/gulicollege/vod-service")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    @ApiOperation("视频上传")
    @PostMapping("/upload-video")
    public Result<String> uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideoAly(file);
        return Result.ok(videoId);
    }

    @ApiOperation("删除视频")
    @DeleteMapping("/remove-video")
    public Result<String> removeVideo(@RequestParam("videoIds")List<String> videoIds) {
        vodService.removeMoreAlyVideo(videoIds);
        return Result.ok();
    }

    @ApiOperation("根据视频id获取视频播放凭证")
    @GetMapping("/get-play-auth/{id}")
    public Result<String> getPlayAuth(@PathVariable String id) {
        if (StringUtils.isEmpty(id))
            throw new GuliException(20001, "非法参数");
        String playAuth = vodService.getPlayAuthById(id);
        return Result.ok(playAuth);
    }
}
