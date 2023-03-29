package com.atcwl.gulicollege.controller.front;

import com.atcwl.gulicollege.entity.EduTeacher;
import com.atcwl.gulicollege.entity.vo.TeacherFrontVO;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.EduTeacherService;
import com.atcwl.gulicollege.vo.EduTeacherQueryVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Api(tags = "讲师前台")
@CrossOrigin
@RestController
@RequestMapping("/edu-service/teacher")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation("前台讲师信息查询")
    @PostMapping("/get-teacher-info-page/{current}/{limit}")
    public Result<Page<EduTeacher>> getTeacherInfoPage(
            @PathVariable Integer current,
            @PathVariable Integer limit
            //@RequestBody(required = false)EduTeacherQueryVO queryVO
            ) {
        Page<EduTeacher> teachersPage = teacherService.findTeachersPage(current, limit, null);
        return Result.ok(teachersPage);
    }

    @ApiOperation("查看讲师详情")
    @GetMapping("/get-teacher-info/{id}")
    public Result<TeacherFrontVO> getTeacherInfo(@PathVariable String id) {
        TeacherFrontVO byId = teacherService.getTeacherInfo(id);
        return Result.ok(byId);
    }
}
