package com.atcwl.gulicollege.service;

import com.atcwl.gulicollege.entity.EduTeacher;
import com.atcwl.gulicollege.entity.vo.TeacherFrontVO;
import com.atcwl.gulicollege.vo.EduTeacherQueryVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author atcwl
 * @since 2023-01-20
 */
public interface EduTeacherService extends IService<EduTeacher> {
    List<EduTeacher> findAllTeachers();
    Page<EduTeacher> findTeachersPage(Integer current, Integer limit, EduTeacherQueryVO eduTeacherQueryVO);
    List<EduTeacher> getIndexTeachers();
    TeacherFrontVO getTeacherInfo(String id);
}
