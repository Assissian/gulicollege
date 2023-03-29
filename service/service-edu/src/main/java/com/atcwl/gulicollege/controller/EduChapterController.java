package com.atcwl.gulicollege.controller;


import com.atcwl.gulicollege.entity.EduChapter;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.EduChapterService;
import com.atcwl.gulicollege.vo.ChapterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atcwl
 * @since 2023-01-26
 */
@Api(tags = "课程章节管理")
@RestController
@RequestMapping("/gulicollege/edu-chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation("获取所有章节及小节")
    @GetMapping("/get-all-chapter-video/{courseId}")
    public Result<List<ChapterVo>> getAllChapterVideo(@PathVariable
                                                          @NotBlank(message = "课程ID不能为空") String courseId) {
        List<ChapterVo> result = eduChapterService.getChapterVideoList(courseId);
        return Result.ok(result);
    }

    @ApiOperation("添加课程章节")
    @PostMapping("/add-chapter")
    public Result<String> addChapter(@RequestBody @Validated EduChapter chapter) {
        boolean save = eduChapterService.save(chapter);
        if (!save)
            throw new GuliException(30001, "保存章节信息失败");
        return Result.ok(chapter.getId());
    }

    @ApiOperation("修改课程章节")
    @PutMapping("/update-chapter")
    public Result<String> updateChapter(@RequestBody @Validated EduChapter chapter) {
        boolean update = eduChapterService.updateById(chapter);
        if (!update)
            throw new GuliException(30001, "修改章节信息失败");
        return Result.ok();
    }

    @ApiOperation("获取指定课程章节信息")
    @GetMapping("/get-chapter/{id}")
    public Result<EduChapter> getChapter(@PathVariable
                                             @NotBlank(message = "id不能为空") String id) {
        EduChapter chapter = eduChapterService.getById(id);
        if (null == chapter)
            throw new GuliException(30001, "获取章节信息异常");
        return Result.ok(chapter);
    }

    @ApiOperation("删除课程章节")
    @DeleteMapping("/remove-chapter/{id}")
    public Result<String> removeChapter(@PathVariable
                                            @NotBlank(message = "id不能为空") String id) {
        boolean remove = eduChapterService.removeById(id);
        if (!remove)
            throw new GuliException(30001, "删除章节异常");
        return Result.ok();
    }
}

