package com.atcwl.gulicollege.schedule;

import com.atcwl.gulicollege.service.StatisticsDailyService;
import com.atcwl.gulicollege.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Component
public class DailyScheduleTask {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 这里的cron定时表达式，可以直接利用网上的工具生成
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduleUpdateDailyData() {
        String formatDate = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.addDailyData(formatDate);
    }
}
