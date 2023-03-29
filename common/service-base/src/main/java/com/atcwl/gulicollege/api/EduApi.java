package com.atcwl.gulicollege.api;

import com.atcwl.gulicollege.dto.OrderCourseDto;
import com.atcwl.gulicollege.result.Result;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@FeignClient(value = "service-edu")
@Component
public interface EduApi {
    @GetMapping("/edu-service/course/get-course-by-id")
    Result<OrderCourseDto> getCourseById(@RequestParam("id") String id);

    @GetMapping("/gulicollege/edu-course/get-daily-data")
    Result<Map<String, Integer>> getDailyData(@RequestParam String day);
}
