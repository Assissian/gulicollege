package com.atcwl.gulicollege.service.impl;

import com.atcwl.gulicollege.api.AliyunApiClient;
import com.atcwl.gulicollege.entity.EduVideo;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.mapper.EduVideoMapper;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author atcwl
 * @since 2023-01-26
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Resource
    private AliyunApiClient vodClient;

    @Override
    public List<EduVideo> getVideos(String courseId) {
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideos = baseMapper.selectList(videoQueryWrapper);
        return eduVideos;
    }

    @Override
    public void removeVideoByCourseId(String courseId) {
        LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getCourseId, courseId);
        queryWrapper.select(EduVideo::getVideoSourceId);
        List<EduVideo> eduVideos = baseMapper.selectList(queryWrapper);
        List<String> videoIds = eduVideos.stream()
                        .map(EduVideo::getVideoSourceId)
                                .collect(Collectors.toList());
        if(videoIds.size()>0) {
            vodClient.removeVideo(videoIds);
        }
        queryWrapper.clear();
        queryWrapper.eq(EduVideo::getCourseId, courseId);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public void removeVideoById(String videoId) {
        LambdaQueryWrapper<EduVideo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduVideo::getId, videoId);
        wrapper.select(EduVideo::getId);
        EduVideo eduVideo = baseMapper.selectOne(wrapper);
        if (null == eduVideo)
            throw new GuliException(30001, "小节ID不正确");
        List<String> videoIds = new ArrayList<>();
        videoIds.add(eduVideo.getVideoSourceId());
        vodClient.removeVideo(videoIds);

        baseMapper.deleteById(videoId);
    }

    @Override
    public void removeVideoSource(String videoSourceId) {
        List<String> videos = Arrays.asList(videoSourceId);
        Result<String> result = vodClient.removeVideo(videos);
        if (result.getCode() != 20000) {
            throw new GuliException(20001, "视频删除失败");
        }
    }

    @Override
    public Integer getVideoViewCount(String day) {
        return baseMapper.selectVideoViewCount(day);
    }
}
