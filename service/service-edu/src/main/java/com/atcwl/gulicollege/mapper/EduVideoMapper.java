package com.atcwl.gulicollege.mapper;

import com.atcwl.gulicollege.entity.EduVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author atcwl
 * @since 2023-01-26
 */
public interface EduVideoMapper extends BaseMapper<EduVideo> {
    Integer selectVideoViewCount(String day);
}
