package com.atcwl.gulicollege.entity.vo;

import com.atcwl.gulicollege.entity.EduCourse;
import com.atcwl.gulicollege.entity.EduTeacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexDataVo {
    private List<EduCourse> eduList;
    private List<EduTeacher> teacherList;
}
