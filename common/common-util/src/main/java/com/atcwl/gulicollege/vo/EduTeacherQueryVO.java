package com.atcwl.gulicollege.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EduTeacherQueryVO {
    //讲师姓名
    private String name;
    //讲师级别
    private Integer level;
    //开始时间
    private Date registerTime;
    //结束时间
    private Date resignTime;
}
