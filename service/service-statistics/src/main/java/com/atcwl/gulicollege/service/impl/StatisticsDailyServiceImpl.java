package com.atcwl.gulicollege.service.impl;

import com.atcwl.gulicollege.api.EduApi;
import com.atcwl.gulicollege.api.UcenterApi;
import com.atcwl.gulicollege.entity.StatisticsDaily;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.mapper.StatisticsDailyMapper;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author atcwl
 * @since 2023-02-08
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterApi ucenterApi;

    @Autowired
    private EduApi eduApi;

    @Override
    public void addDailyData(String day) {
        Result<Map<String, Integer>> userData = ucenterApi.getDailyData(day);
        if (userData.getCode() != 20000)
            throw new GuliException(userData.getCode(), userData.getMessage());
        Map<String, Integer> result = userData.getData();
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setRegisterNum(result.get("register_no"));
        statisticsDaily.setLoginNum(result.get("login_no"));

        Result<Map<String, Integer>> eduData = eduApi.getDailyData(day);
        if (eduData.getCode() != 20000)
            throw new GuliException(eduData.getCode(), eduData.getMessage());
        Map<String, Integer> eduResult = eduData.getData();
        statisticsDaily.setCourseNum(eduResult.get("course_no"));
        statisticsDaily.setVideoViewNum(eduResult.get("video_view_no"));

        int insert = baseMapper.insert(statisticsDaily);
        if (insert <= 0)
            throw new GuliException(20001, "统计数据更新失败");
    }

    @Override
    public Map<String, List> getDayData(String type, String begin, String end) {
        Map<String, List> map = new HashMap<>();
        LambdaQueryWrapper<StatisticsDaily> qw = new LambdaQueryWrapper<>();
        qw.between(StatisticsDaily::getDateCalculated, begin, end);
        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(qw);
        List<String> days = new ArrayList<>();
        List<Integer> dataCount = new ArrayList<>();
        try {
            for (StatisticsDaily statisticsDaily : statisticsDailies) {
                days.add(statisticsDaily.getDateCalculated());
                Integer count = (Integer) statisticsDaily.getClass().getMethod(StringUtils.underlineToCamel(type)).invoke(statisticsDaily);
                dataCount.add(count);
            }
        } catch (Exception e) {
            throw new GuliException(20001, e.getMessage());
        }
        map.put("days", days);
        map.put("countData", dataCount);
        return map;
    }
}
