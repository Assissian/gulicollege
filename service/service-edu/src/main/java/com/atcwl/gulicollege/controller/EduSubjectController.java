package com.atcwl.gulicollege.controller;


import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.EduSubjectService;
import com.atcwl.gulicollege.vo.SubjectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atcwl
 * @since 2023-01-27
 */
@Api(tags = "课程分类管理")
@RestController
@RequestMapping("/gulicollege/edu-subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @ApiOperation("添加课程分类信息")
    @PostMapping("/add-subjects")
    public Result<String> addSubjects(MultipartFile file) {
        eduSubjectService.saveSubjects(file);
        return Result.ok();
    }

    @ApiOperation("获取所有课程分类")
    @GetMapping("/get-subjects")
    public Result<List<SubjectVO>> getSubject() {
        List<SubjectVO> result = eduSubjectService.findAllSubjects();
        return Result.ok(result);
    }
}

