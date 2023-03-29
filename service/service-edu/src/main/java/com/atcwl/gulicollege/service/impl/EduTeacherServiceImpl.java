package com.atcwl.gulicollege.service.impl;

import com.atcwl.gulicollege.entity.EduTeacher;
import com.atcwl.gulicollege.entity.vo.CourseFrontVo;
import com.atcwl.gulicollege.entity.vo.TeacherFrontVO;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.mapper.EduTeacherMapper;
import com.atcwl.gulicollege.service.EduCourseService;
import com.atcwl.gulicollege.service.EduTeacherService;
import com.atcwl.gulicollege.vo.CourseInfoVo;
import com.atcwl.gulicollege.vo.EduTeacherQueryVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author atcwl
 * @since 2023-01-20
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    @Autowired
    private EduCourseService courseService;

    @Override
    public List<EduTeacher> findAllTeachers() {
        return baseMapper.selectList(null);
    }

    @Override
    public Page<EduTeacher> findTeachersPage(Integer current, Integer limit, EduTeacherQueryVO eduTeacherQueryVO) {
        //这里可以选择用Wrapper来拼接条件，也可以直接写动态SOL
        LambdaQueryWrapper<EduTeacher> queryWrapper = new LambdaQueryWrapper<>();
        if (null != eduTeacherQueryVO) {
            queryWrapper.like(StringUtils.hasLength(eduTeacherQueryVO.getName()), EduTeacher::getName, eduTeacherQueryVO.getName());
            Integer level = eduTeacherQueryVO.getLevel();
            queryWrapper.eq(level != null, EduTeacher::getLevel, level);
            Date createTime = eduTeacherQueryVO.getRegisterTime();
            Date endTime = eduTeacherQueryVO.getResignTime();
            queryWrapper.ge(null != createTime, EduTeacher::getGmtCreate, createTime);
            queryWrapper.le(null != endTime, EduTeacher::getGmtCreate, endTime);
        }
        //构造分页对象
        Page<EduTeacher> page = new Page<>(current, limit);
        baseMapper.selectPage(page, queryWrapper);
        return page;
    }

    @Override
    public List<EduTeacher> getIndexTeachers() {
        QueryWrapper<EduTeacher> qw = new QueryWrapper<>();
        qw.orderByAsc("sort");
        qw.last("limit 4");
        List<EduTeacher> eduTeachers = baseMapper.selectList(qw);
        return eduTeachers;
    }

    @Override
    public TeacherFrontVO getTeacherInfo(String id) {
        EduTeacher eduTeacher = baseMapper.selectById(id);
        if (null == eduTeacher)
            throw new GuliException(30002, "获取该讲师信息失败");
        List<CourseInfoVo> courses = courseService.getCourseByTeacherId(id);
        TeacherFrontVO teacherFrontVO = new TeacherFrontVO();
        teacherFrontVO.setTeacher(eduTeacher);
        teacherFrontVO.setCourseList(courses);
        return teacherFrontVO;
    }
}
