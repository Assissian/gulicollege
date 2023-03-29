package com.atcwl.gulicollege.mapper;

import com.atcwl.gulicollege.entity.EduSubject;
import com.atcwl.gulicollege.vo.SubjectVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author atcwl
 * @since 2023-01-27
 */
public interface EduSubjectMapper extends BaseMapper<EduSubject> {
    List<SubjectVO> selectSubjectTree(String parenId);
}
