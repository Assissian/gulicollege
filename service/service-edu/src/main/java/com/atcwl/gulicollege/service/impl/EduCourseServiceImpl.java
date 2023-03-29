package com.atcwl.gulicollege.service.impl;

import com.atcwl.gulicollege.api.OrderApi;
import com.atcwl.gulicollege.dto.OrderCourseDto;
import com.atcwl.gulicollege.entity.EduCourse;
import com.atcwl.gulicollege.entity.EduCourseDescription;
import com.atcwl.gulicollege.entity.EduVideo;
import com.atcwl.gulicollege.entity.vo.CourseFrontVo;
import com.atcwl.gulicollege.entity.vo.CourseWebVo;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.mapper.EduCourseMapper;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.EduChapterService;
import com.atcwl.gulicollege.service.EduCourseDescriptionService;
import com.atcwl.gulicollege.service.EduCourseService;
import com.atcwl.gulicollege.service.EduVideoService;
import com.atcwl.gulicollege.vo.ChapterVo;
import com.atcwl.gulicollege.vo.CourseInfoVo;
import com.atcwl.gulicollege.vo.CoursePublishVo;
import com.atcwl.gulicollege.vo.CourseQueryVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atcwl
 * @since 2023-01-26
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService descriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private OrderApi orderApi;

    @Override
    public String saveNewCourse(CourseInfoVo course) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(course, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new GuliException(30001, "课程信息保存失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(course.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        boolean save = descriptionService.save(eduCourseDescription);
        if (!save)
            throw new GuliException(30001, "课程描述保存失败");
        return eduCourse.getId();
    }

    @Override
    public void updateCourseInfo(CourseInfoVo course) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(course, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update <= 0)
            throw new GuliException(30003, "课程信息修改失败");
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(course.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        boolean updateDes = descriptionService.updateById(eduCourseDescription);
        if (!updateDes)
            throw new GuliException(30001, "课程描述修改失败");
    }

    @Override
    public CourseInfoVo getCourseInfoById(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        if (null == eduCourse)
            throw new GuliException(30003, "获取课程信息异常");
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        EduCourseDescription description = descriptionService.getById(eduCourse.getId());
        courseInfoVo.setDescription(description.getDescription());
        return courseInfoVo;
    }

    @Override
    public CoursePublishVo getPublishCourse(String id) {
        CoursePublishVo coursePublishVo = baseMapper.selectPublishCourseById(id);
        if (null == coursePublishVo)
            throw new GuliException(30002, "查询课程发布信息失败");
        return coursePublishVo;
    }

    @Override
    public void publishCourse(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        eduCourse.setStatus("Normal");
        baseMapper.updateById(eduCourse);
    }

    @Override
    public List<EduCourse> getCourseList() {
        List<EduCourse> eduCourses = baseMapper.selectList(null);
        if (null == eduCourses)
            throw new GuliException(30002, "错误，未能查询到课程列表信息");
        return eduCourses;
    }

    @Override
    public void removeCourseById(String id) {
        //先删除对应小节
        eduVideoService.removeVideoByCourseId(id);
        //再删除对应章节
        eduChapterService.removeChapterByCourseId(id);
        //然后删除对应课程描述
        boolean remove = descriptionService.removeById(id);
        //最后删除课程
        int delete = baseMapper.deleteById(id);
        if (delete <= 0)
            throw new GuliException(30002, "删除课程失败");
    }

    @Override
    public Page<EduCourse> getCoursePages(Integer current, Integer limit, CourseQueryVO courseQueryVO) {
        Page<EduCourse> page = new Page<>(current, limit);
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        String title = courseQueryVO.getTitle();
        String status = courseQueryVO.getStatus();
        queryWrapper.like(StringUtils.hasLength(title), EduCourse::getTitle, title);
        queryWrapper.eq("Normal".equals(status) || "Draft".equals(status), EduCourse::getStatus, status);
        baseMapper.selectPage(page, queryWrapper);
        return page;
    }

    @Override
    public List<EduCourse> getIndexCourses() {
        QueryWrapper<EduCourse> qw = new QueryWrapper<>();
        qw.eq("status", "Normal");
        qw.orderByAsc("view_count");
        qw.last("limit 8");
        List<EduCourse> eduCourses = baseMapper.selectList(qw);
        return eduCourses;
    }

    @Override
    public Page<EduCourse> getCourseFrontList(Integer current, Integer limit, CourseFrontVo courseFrontVo) {
        Page<EduCourse> page = new Page<>(current, limit);
        LambdaQueryWrapper<EduCourse> qw = new LambdaQueryWrapper<>();
        qw.eq(EduCourse::getStatus, "Normal");
        if (null != courseFrontVo) {
            qw.like(StringUtils.hasLength(courseFrontVo.getTitle()), EduCourse::getTitle, courseFrontVo.getTitle());
            qw.eq(StringUtils.hasLength(courseFrontVo.getTeacherId()), EduCourse::getTeacherId, courseFrontVo.getTeacherId());
            qw.eq(StringUtils.hasLength(courseFrontVo.getSubjectId()), EduCourse::getSubjectId, courseFrontVo.getSubjectId());
            qw.eq(StringUtils.hasLength(courseFrontVo.getSubjectParentId()), EduCourse::getSubjectParentId, courseFrontVo.getSubjectParentId());
            qw.orderByDesc(StringUtils.hasLength(courseFrontVo.getBuyCountSort()), EduCourse::getBuyCount);
            qw.orderByDesc(StringUtils.hasLength(courseFrontVo.getGmtCreateSort()), EduCourse::getGmtCreate);
            qw.orderByDesc(StringUtils.hasLength(courseFrontVo.getPriceSort()), EduCourse::getPrice);
        }
        baseMapper.selectPage(page, qw);
        return page;
    }

    @Override
    public Map<String, Object> getBaseCourse(String id, String memberId) {
        CourseWebVo courseWebVo = baseMapper.selectBaseCourseInfo(id);
        if (null == courseWebVo)
            throw new GuliException(30001, "获取课程详情失败 ");
        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoList(id);
        if (null == chapterVideoList)
            throw new GuliException(30001, "获取课程详情失败");
        Boolean isBuy = false;
        //这里需要额外判断课程是否已经被特定的用户购买
        if (StringUtils.hasLength(memberId)) {
            Result<Boolean> booleanResult = orderApi.queryBuyCourse(id, memberId);
            if (20000 != booleanResult.getCode())
                throw new GuliException(20001, "获取课程购买信息失败");
            isBuy = booleanResult.getData();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("courseWebVo", courseWebVo);
        map.put("chapterVideoList", chapterVideoList);
        map.put("isBuy", isBuy);
        return map;
    }

    @Override
    public List<CourseInfoVo> getCourseByTeacherId(String teacherId) {
        LambdaQueryWrapper<EduCourse> qw = new LambdaQueryWrapper<>();
        qw.eq(EduCourse::getTeacherId, teacherId);
        qw.eq(EduCourse::getStatus, "Normal");
        List<EduCourse> eduCourses = baseMapper.selectList(qw);
        List<CourseInfoVo> courseInfoVos = eduCourses.stream()
                .map(eduCourse -> {
                    CourseInfoVo courseInfoVo = new CourseInfoVo();
                    BeanUtils.copyProperties(eduCourse, courseInfoVo);
                    return courseInfoVo;
                })
                .collect(Collectors.toList());
        return courseInfoVos;
    }

    @Override
    public OrderCourseDto getCourseWithOrder(String id) {
        CourseWebVo courseWebVo = baseMapper.selectBaseCourseInfo(id);
        OrderCourseDto orderCourseDto = new OrderCourseDto();
        BeanUtils.copyProperties(courseWebVo, orderCourseDto);
        return orderCourseDto;
    }

    @Override
    public Map<String, Integer> getDailyCourseAndVideoData(String day) {
        Map<String, Integer> map = new HashMap<>();
        Integer courseNo = baseMapper.selectCourseDailyCount(day);
        Integer videoViewNo = eduVideoService.getVideoViewCount(day);
        map.put("course_no", courseNo);
        map.put("video_view_no", videoViewNo);
        return map;
    }
}
