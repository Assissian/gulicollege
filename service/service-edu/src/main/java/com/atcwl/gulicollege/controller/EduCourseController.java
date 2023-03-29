package com.atcwl.gulicollege.controller;


import com.atcwl.gulicollege.entity.EduCourse;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.EduCourseService;
import com.atcwl.gulicollege.vo.CourseInfoVo;
import com.atcwl.gulicollege.vo.CoursePublishVo;
import com.atcwl.gulicollege.vo.CourseQueryVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atcwl
 * @since 2023-01-26
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/gulicollege/edu-course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    /**
     *
     * @param course 对于CourseInfoVO参数我们使用的Hibernate-validator的注解来进行参数校验
     * @return
     */
    @ApiOperation("添加课程信息")
    @PostMapping("/add-course")
    public Result<String> addCourse(@RequestBody @Validated CourseInfoVo course) {
        String courseId = eduCourseService.saveNewCourse(course);
        return Result.ok(courseId);
    }

    @ApiOperation("修改课程信息")
    @PutMapping("/update-course")
    public Result<String> updateCourse(@RequestBody @Validated CourseInfoVo course) {
        eduCourseService.updateCourseInfo(course);
        return Result.ok();
    }

    @ApiOperation("获取指定课程信息")
    @GetMapping("/get-course/{id}")
    public Result<CourseInfoVo> getCourse(@PathVariable String id) {
        CourseInfoVo result = eduCourseService.getCourseInfoById(id);
        return Result.ok(result);
    }

    @ApiOperation("获取课程发布信息")
    @GetMapping("/get-publish-course-info/{id}")
    public Result<CoursePublishVo> getPublishCourseInfo(@PathVariable
                                                        @NotBlank(message = "课程ID不能为空") String id) {
        CoursePublishVo coursePublishVo = eduCourseService.getPublishCourse(id);
        return Result.ok(coursePublishVo);
    }

    @ApiOperation("发布课程")
    @PutMapping("/publish-course/{id}")
    public Result<String> publishCourseInfo(@PathVariable
                                        @NotBlank(message = "课程ID不能为空") String id) {
         eduCourseService.publishCourse(id);
         return Result.ok();
    }

    @ApiOperation("获取所有课程")
    @GetMapping("/get-all-courses")
    public Result<List<EduCourse>> getAllCourses() {
        List<EduCourse> result =  eduCourseService.getCourseList();
        return Result.ok(result);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/remove-course/{id}")
    public Result<String> removeCourse(@PathVariable
                                           @NotBlank(message = "课程ID不能为空") String id) {
        eduCourseService.removeCourseById(id);
        return Result.ok();
    }

    @ApiOperation("获取课程分页")
    @PostMapping("/get-course-page/{current}/{limit}")
    public Result<Page<EduCourse>> getCoursePage(
            @PathVariable Integer current,
            @PathVariable Integer limit,
            @RequestBody (required = false)CourseQueryVO courseQueryVO
            ) {
        Page<EduCourse> page = eduCourseService.getCoursePages(current, limit, courseQueryVO);
        return Result.ok(page);
    }

    @GetMapping("/get-daily-data")
    public Result<Map<String, Integer>> getDailyData(@RequestParam String day) {
        Map<String, Integer> map = eduCourseService.getDailyCourseAndVideoData(day);
        return Result.ok(map);
    }
}

