package com.atcwl.gulicollege.service;

import com.atcwl.gulicollege.entity.EduSubject;
import com.atcwl.gulicollege.vo.SubjectVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author atcwl
 * @since 2023-01-27
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubjects(MultipartFile file);

    List<SubjectVO> findAllSubjects();
}
