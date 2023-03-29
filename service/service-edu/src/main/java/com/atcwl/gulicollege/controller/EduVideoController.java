package com.atcwl.gulicollege.controller;


import com.atcwl.gulicollege.entity.EduVideo;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author atcwl
 * @since 2023-01-26
 */
@Api(tags = "课程小节管理")
@RestController
@RequestMapping("/gulicollege/edu-video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @ApiOperation("保存小节信息")
    @PostMapping("/add-video")
    public Result<String> addVideo(@RequestBody EduVideo video) {
        boolean save = eduVideoService.save(video);
        if (!save)
            throw new GuliException(30001, "保存小节信息失败");
        return Result.ok();
    }

    @ApiOperation("删除小节信息")
    @DeleteMapping("/remove-video/{id}")
    public Result<String> removeVideo(@PathVariable
                                      @NotBlank(message = "小节ID不能为空") String id) {
        eduVideoService.removeVideoById(id);
        return Result.ok();
    }

    @ApiOperation("删除小节信息")
    @DeleteMapping("/remove-video-source/{videoSourceId}")
    public Result<String> removeVideoSource(@PathVariable
                                      @NotBlank(message = "视频源ID不能为空") String videoSourceId) {
        eduVideoService.removeVideoSource(videoSourceId);
        return Result.ok();
    }

    @ApiOperation("更新小节信息")
    @PutMapping("/update-video")
    public Result<String> updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return Result.ok();
    }

    @ApiOperation("查询小节信息")
    @GetMapping("/get-video/{id}")
    public Result<EduVideo> getVideo(@PathVariable String id) {
        EduVideo byId = eduVideoService.getById(id);
        if (null == byId)
            throw new GuliException(30002, "为获取到小节信息");
        return Result.ok(byId);
    }
}

