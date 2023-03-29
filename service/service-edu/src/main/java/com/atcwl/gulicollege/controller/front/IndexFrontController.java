package com.atcwl.gulicollege.controller.front;

import com.atcwl.gulicollege.entity.EduCourse;
import com.atcwl.gulicollege.entity.EduTeacher;
import com.atcwl.gulicollege.entity.vo.IndexDataVo;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.EduCourseService;
import com.atcwl.gulicollege.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Api(tags = "前台幻灯片管理")
@RestController
@RequestMapping("/edu-service/front")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation("获取首页上的热门课程和名师数据")
    @GetMapping("/index")
    public Result<IndexDataVo> getIndexData() {
        List<EduCourse> courses = courseService.getIndexCourses();
        List<EduTeacher> teachers = teacherService.getIndexTeachers();
        if (null == courses || null == teachers)
            throw new GuliException(30001, "获取信息失败");
        IndexDataVo indexDataVo = new IndexDataVo(courses, teachers);
        return Result.ok(indexDataVo);
    }
}
