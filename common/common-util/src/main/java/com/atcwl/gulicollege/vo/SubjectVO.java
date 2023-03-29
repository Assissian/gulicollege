package com.atcwl.gulicollege.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Data
public class SubjectVO {
    private String id;
    private String title;
    private List<SubjectVO> children;
}
