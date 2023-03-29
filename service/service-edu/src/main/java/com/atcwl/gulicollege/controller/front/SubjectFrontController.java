package com.atcwl.gulicollege.controller.front;

import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.EduSubjectService;
import com.atcwl.gulicollege.vo.SubjectVO;
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
@Api(tags = "课程分类")
@RestController
@RequestMapping("/edu-service/subject")
@CrossOrigin
public class SubjectFrontController {
    @Autowired
    private EduSubjectService subjectService;

    @ApiOperation("查询所有课程分类")
    @GetMapping("/get-subject-page")
    public Result<List<SubjectVO>> getSubjectPage() {
        List<SubjectVO> allSubjects = subjectService.findAllSubjects();
        return Result.ok(allSubjects);
    }
}
