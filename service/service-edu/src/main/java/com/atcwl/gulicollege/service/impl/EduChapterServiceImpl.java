package com.atcwl.gulicollege.service.impl;

import com.atcwl.gulicollege.entity.EduChapter;
import com.atcwl.gulicollege.entity.EduVideo;
import com.atcwl.gulicollege.mapper.EduChapterMapper;
import com.atcwl.gulicollege.service.EduChapterService;
import com.atcwl.gulicollege.service.EduVideoService;
import com.atcwl.gulicollege.vo.ChapterVo;
import com.atcwl.gulicollege.vo.VideoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoList(String courseId) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);

        List<EduVideo> eduVideos = eduVideoService.getVideos(courseId);

        List<ChapterVo> chapterVideoList = eduChapters.parallelStream()
                .map(eduChapter -> {
                    ChapterVo chapterVo = new ChapterVo();
                    chapterVo.setId(eduChapter.getId());
                    chapterVo.setTitle(eduChapter.getTitle());
                    List<VideoVo> children = new ArrayList<>();
                    Map<EduVideo, Integer> remove = new HashMap<>();
                    for (int i = 0; i < eduVideos.size(); i++) {
                        EduVideo next = eduVideos.get(i);
                        if (eduChapter.getId().equals(next.getChapterId())) {
                            VideoVo videoVo = new VideoVo();
                            videoVo.setId(next.getId());
                            videoVo.setTitle(next.getTitle());
                            videoVo.setVideoSourceId(next.getVideoSourceId());
                            videoVo.setIsFree(next.getIsFree());
                            children.add(videoVo);
                        }
                        remove.put(next, i);
                    }
                    remove.forEach((eduVideo, index) -> {
                        eduVideos.remove(index);
                    });
                    chapterVo.setChildren(children);
                    return chapterVo;
                })
                .collect(Collectors.toList());
        return chapterVideoList;
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        LambdaQueryWrapper<EduChapter> qw = new LambdaQueryWrapper<>();
        qw.eq(EduChapter::getCourseId, courseId);
        baseMapper.delete(qw);
    }
}
