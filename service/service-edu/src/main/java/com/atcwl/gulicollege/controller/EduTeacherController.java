package com.atcwl.gulicollege.controller;


import com.atcwl.gulicollege.entity.EduTeacher;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.EduTeacherService;
import com.atcwl.gulicollege.vo.EduTeacherQueryVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atcwl
 * @since 2023-01-20
 * Restful风格：路径需要以“-“来链接单词，参数名称则使用”_“来连接
 */
@Api(tags = "讲师管理")
@CrossOrigin
@RestController
@RequestMapping("/gulicollege/edu-teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("所有讲师")
    @GetMapping("/get-all-teachers")
    public Result<List<EduTeacher>> getAllTeachers() {
        List<EduTeacher> allTeachers = eduTeacherService.findAllTeachers();
        return allTeachers == null ? Result.fail() : Result.ok(allTeachers);
    }

    @ApiOperation("获取指定讲师")
    @GetMapping("/get-teacher-by-id/{id}")
    public Result<EduTeacher> getTeacherById(@PathVariable String id) {
        if(!StringUtils.hasLength(id)) {
            Result<EduTeacher> fail = Result.fail();
            fail.message("请选择指定讲师后再做操作");
            return fail;
        }
        EduTeacher teacher = eduTeacherService.getById(id);
        return teacher != null ? Result.ok(teacher) : Result.fail();
    }

    @ApiOperation("分页获取讲师")
    @PostMapping("/get-teacher-page/{current}/{limit}")
    public Result<Page<EduTeacher>> getTeacherPage(@PathVariable Integer current,
                                                   @PathVariable Integer limit,
                                                   @RequestBody(required = false) EduTeacherQueryVO eduTeacherQueryVO) {
        if(null == current || null == limit)
            throw new RuntimeException("当前页数错误");

        Page<EduTeacher> page = eduTeacherService.findTeachersPage(current, limit, eduTeacherQueryVO);
        return Result.ok(page);
    }

    @ApiOperation("新增讲师信息")
    @PostMapping("/save-teacher")
    public Result<String> saveTeacher(@RequestBody EduTeacher teacher) {
        if(!StringUtils.hasLength(teacher.getName()) || (teacher.getLevel() <1 || teacher.getLevel() > 2)
        || (teacher.getSort() < 0 || teacher.getSort() > 10) || !StringUtils.hasLength(teacher.getCareer())) {
            throw new GuliException(30003, "请填写正确的讲师信息");
        }
        boolean save = eduTeacherService.save(teacher);

        return save ? Result.ok() : Result.fail();
    }

    @ApiOperation("修改讲师信息")
    @PutMapping("/update-teacher-info")
    public Result<String> updateTeacherInfo(@RequestBody EduTeacher teacher) {
        if(!StringUtils.hasLength(teacher.getName()) || (teacher.getLevel() <1 || teacher.getLevel() > 2)
                || (teacher.getSort() < 0 || teacher.getSort() > 10) || !StringUtils.hasLength(teacher.getCareer())) {
            throw new GuliException(30003, "请填写正确的讲师信息");
        }
        boolean update = eduTeacherService.updateById(teacher);
        return update ? Result.ok() : Result.fail();
    }

    @ApiOperation("删除讲师")
    @DeleteMapping("/remove-teacher/{id}")
    public Result<String> removeTeacher(@PathVariable String id) {
        if(!StringUtils.hasLength(id))
            throw new GuliException(30004, "请指定对应讲师然后再删除！");
        EduTeacher byId = eduTeacherService.getById(id);
        if(null == byId)
            return Result.fail("该讲师不存在");
        boolean remove = eduTeacherService.removeById(id);
        return remove ? Result.ok() : Result.fail();
    }
}

