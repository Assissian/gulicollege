package com.atcwl.gulicollege.service;

import com.atcwl.gulicollege.dto.OrderCourseDto;
import com.atcwl.gulicollege.entity.EduCourse;
import com.atcwl.gulicollege.entity.vo.CourseFrontVo;
import com.atcwl.gulicollege.entity.vo.CourseWebVo;
import com.atcwl.gulicollege.vo.CourseInfoVo;
import com.atcwl.gulicollege.vo.CoursePublishVo;
import com.atcwl.gulicollege.vo.CourseQueryVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atcwl
 * @since 2023-01-26
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveNewCourse(CourseInfoVo course);

    void updateCourseInfo(CourseInfoVo course);

    CourseInfoVo getCourseInfoById(String id);

    CoursePublishVo getPublishCourse(String id);

    void publishCourse(String id);

    List<EduCourse> getCourseList();

    void removeCourseById(String id);

    Page<EduCourse> getCoursePages(Integer current, Integer limit, CourseQueryVO courseQueryVO);

    List<EduCourse> getIndexCourses();

    Page<EduCourse> getCourseFrontList(Integer current, Integer limit, CourseFrontVo courseFrontVo);

    Map<String, Object> getBaseCourse(String id, String memberId);

    List<CourseInfoVo> getCourseByTeacherId(String teacherId);

    OrderCourseDto getCourseWithOrder(String id);

    Map<String, Integer> getDailyCourseAndVideoData(String day);
}
