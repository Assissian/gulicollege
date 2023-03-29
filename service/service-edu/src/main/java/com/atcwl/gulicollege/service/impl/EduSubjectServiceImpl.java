package com.atcwl.gulicollege.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atcwl.gulicollege.entity.EduSubject;
import com.atcwl.gulicollege.entity.excel.ExcelSubject;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.listener.ExcelListener;
import com.atcwl.gulicollege.mapper.EduSubjectMapper;
import com.atcwl.gulicollege.service.EduSubjectService;
import com.atcwl.gulicollege.vo.SubjectVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atcwl
 * @since 2023-01-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Override
    public void saveSubjects(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubject.class, new ExcelListener(this)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(50001, "文件读取错误");
        }
    }

    @Override
    public List<SubjectVO> findAllSubjects() {
        // TODO
        //目前为基本做法，有待优化

        /** 递归  —— 成功**/
        //List<SubjectVO> subjectVOS = baseMapper.selectSubjectTree("0");

        /** stream流测试成功
         *  这里还可以使用并行流来完成对应的操作，效率会更高
         * **/
        //List<EduSubject> all = baseMapper.selectList(null);
        //List<EduSubject> ones = all.stream()
        //        .filter(eduSubject -> {
        //            if ("0".equals(eduSubject.getParentId()))
        //                return true;
        //            return false;
        //        })
        //        .collect(Collectors.toList());
        //List<EduSubject> twos = all.stream()
        //        .filter(eduSubject -> {
        //            if (!"0".equals(eduSubject.getParentId()))
        //                return true;
        //            return false;
        //        })
        //        .collect(Collectors.toList());
        //List<SubjectVO> result = ones.stream()
        //        .map(eduSubject -> {
        //            List<SubjectVO> collect = twos.stream()
        //                    .filter(two -> {
        //                        if (two.getParentId().equals(eduSubject.getId()))
        //                            return true;
        //                        return false;
        //                    })
        //                    .map(two -> {
        //                        SubjectVO twoVO = new SubjectVO();
        //                        twoVO.setId(two.getId());
        //                        twoVO.setTitle(two.getTitle());
        //                        return twoVO;
        //                    })
        //                    .collect(Collectors.toList());
        //            SubjectVO subjectVO = new SubjectVO();
        //            subjectVO.setId(eduSubject.getId());
        //            subjectVO.setTitle(eduSubject.getTitle());
        //            subjectVO.setChildren(collect);
        //            return subjectVO;
        //        })
        //        .collect(Collectors.toList());
        //可以从算法层面优化，或是stream流，或是递归查询
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjects = baseMapper.selectList(queryWrapper);
        if (null == oneSubjects) {
            throw new GuliException(30001, "查询一级分类失败！");
        }
        queryWrapper.clear();
        queryWrapper.ne("parent_id", "0");
        List<EduSubject> twoSubjects = baseMapper.selectList(queryWrapper);
        if (null == twoSubjects) {
            throw new GuliException(30001, "查询二级分类失败！ ");
        }

        List<SubjectVO> subjectVOList = new ArrayList<>();
        for (EduSubject oneSubject : oneSubjects) {
            SubjectVO oneSubjectVO = new SubjectVO();
            oneSubjectVO.setId(oneSubject.getId());
            oneSubjectVO.setTitle(oneSubject.getTitle());

            List<SubjectVO> children = new ArrayList<>();
            for (EduSubject twoSubject : twoSubjects) {
                if (twoSubject.getParentId().equals(oneSubject.getId())) {
                    SubjectVO twoSubjectVO = new SubjectVO();
                    twoSubjectVO.setId(twoSubject.getId());
                    twoSubjectVO.setTitle(twoSubject.getTitle());
                    children.add(twoSubjectVO);
                }
            }
            oneSubjectVO.setChildren(children);
            subjectVOList.add(oneSubjectVO);
        }
        return subjectVOList;
    }
}
