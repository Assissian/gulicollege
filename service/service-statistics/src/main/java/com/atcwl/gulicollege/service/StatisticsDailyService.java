package com.atcwl.gulicollege.service;

import com.atcwl.gulicollege.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author atcwl
 * @since 2023-02-08
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void addDailyData(String day);

    Map<String, List> getDayData(String type, String begin, String end);
}
