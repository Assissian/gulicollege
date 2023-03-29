package com.atcwl.gulicollege.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atcwl.gulicollege.entity.EduSubject;
import com.atcwl.gulicollege.entity.excel.ExcelSubject;
import com.atcwl.gulicollege.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
public class ExcelListener extends AnalysisEventListener<ExcelSubject> {
    private EduSubjectService eduSubjectService;
    private List<String> oneSubjects;
    private List<String> twoSubjects;

    public ExcelListener() {
    }

    public ExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", "0");
        oneSubjects = eduSubjectService.list(queryWrapper)
                .stream()
                .map(EduSubject::getTitle)
                .collect(Collectors.toList());
        queryWrapper.clear();
        queryWrapper.ne("parent_id", "0");
        twoSubjects = eduSubjectService.list(queryWrapper)
                .stream()
                .map(EduSubject::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public void invoke(ExcelSubject excelSubject, AnalysisContext analysisContext) {
        String oneSubjectName = excelSubject.getOneSubjectName();
        EduSubject one = new EduSubject();
        if (!oneSubjects.contains(oneSubjectName)) {
            one.setParentId("0");
            one.setTitle(oneSubjectName);
            one.setSort(0);
            eduSubjectService.save(one);
        } else {
            one = eduSubjectService.getOne(new LambdaUpdateWrapper<EduSubject>().eq(EduSubject::getTitle, oneSubjectName));
        }
        String twoSubjectName = excelSubject.getTwoSubjectName();
        EduSubject two = new EduSubject();
        if (!twoSubjects.contains(twoSubjectName)) {
            two.setParentId(one.getId());
            two.setTitle(twoSubjectName);
            two.setSort(0);
            eduSubjectService.save(two);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
