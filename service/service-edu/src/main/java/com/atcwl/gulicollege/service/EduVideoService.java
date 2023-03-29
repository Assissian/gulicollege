package com.atcwl.gulicollege.service;

import com.atcwl.gulicollege.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author atcwl
 * @since 2023-01-26
 */
public interface EduVideoService extends IService<EduVideo> {

    List<EduVideo> getVideos(String courseId);

    void removeVideoByCourseId(String courseId);

    void removeVideoById(String videoId);

    void removeVideoSource(String videoSourceId);

    Integer getVideoViewCount(String day);
}
