package com.atcwl.gulicollege.service;

import com.atcwl.gulicollege.entity.EduChapter;
import com.atcwl.gulicollege.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atcwl
 * @since 2023-01-26
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoList(String courseId);

    void removeChapterByCourseId(String courseId);
}
