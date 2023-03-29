package com.atcwl.gulicollege.entity.vo;

import com.atcwl.gulicollege.entity.EduCourse;
import com.atcwl.gulicollege.entity.EduTeacher;
import com.atcwl.gulicollege.vo.CourseInfoVo;
import lombok.Data;

import java.util.List;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Data
public class TeacherFrontVO {
    private EduTeacher teacher;
    private List<CourseInfoVo> courseList;
}
