package com.atcwl.gulicollege.mapper;

import com.atcwl.gulicollege.entity.EduCourse;
import com.atcwl.gulicollege.entity.vo.CourseWebVo;
import com.atcwl.gulicollege.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author atcwl
 * @since 2023-01-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo selectPublishCourseById(String courseId);
    int deleteCourseCascade(String courseId);
    CourseWebVo selectBaseCourseInfo(String id);
    Integer selectCourseDailyCount(String day);
}
