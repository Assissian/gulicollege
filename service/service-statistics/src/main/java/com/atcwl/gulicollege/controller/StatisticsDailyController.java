package com.atcwl.gulicollege.controller;


import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author atcwl
 * @since 2023-02-08
 */
@Api(tags = "统计数据管理")
@RestController
@RequestMapping("/gulicollege/service-statistics")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @PostMapping("/create-daily-data/{day}")
    public Result<String> createDailyData(@PathVariable String day) {
        if (StringUtils.isEmpty(day))
            throw new GuliException(20001, "日期不能为空");
        statisticsDailyService.addDailyData(day);
        return Result.ok();
    }
    
    @GetMapping("/get-day-data/{type}/{begin}/{end}")
    public Result<Map<String, List>> getDayData(@PathVariable String type,
                                                   @PathVariable String begin,
                                                   @PathVariable String end
    ) {
        Map<String, List>map = statisticsDailyService.getDayData(type, begin, end);
        return Result.ok(map);
    }
}

