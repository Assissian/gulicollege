package com.atcwl.gulicollege.controller.front;

import com.atcwl.gulicollege.dto.OrderCourseDto;
import com.atcwl.gulicollege.entity.EduCourse;
import com.atcwl.gulicollege.entity.vo.CourseFrontVo;
import com.atcwl.gulicollege.entity.vo.CourseWebVo;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.EduCourseService;
import com.atcwl.gulicollege.util.JwtUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Api(tags = "课程信息查询")
@RestController
@RequestMapping("/edu-service/course")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @ApiOperation("获取全部课程信息")
    @PostMapping("/get-courses-info/{current}/{limit}")
    public Result<Page<EduCourse>> getCoursesInfo(
            @PathVariable Integer current,
            @PathVariable Integer limit,
            @RequestBody(required = false)CourseFrontVo courseFrontVo
            ) {
        Page<EduCourse> page =  courseService.getCourseFrontList(current, limit, courseFrontVo);
        return Result.ok(page);
    }

    @ApiOperation("查看课程详情")
    @GetMapping("/get-base-course/{id}")
    public Result<Map<String, Object>> getBaseCourse(@PathVariable String id, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Map<String, Object> baseCourse = courseService.getBaseCourse(id, memberId);
        return Result.ok(baseCourse);
    }

    @GetMapping("/get-course-by-id")
    public Result<OrderCourseDto> getCourseById(@RequestParam String id) {
        OrderCourseDto result = courseService.getCourseWithOrder(id);
        return Result.ok(result);
    }
}
