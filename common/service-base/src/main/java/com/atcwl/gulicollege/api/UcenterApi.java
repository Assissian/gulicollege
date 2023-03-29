package com.atcwl.gulicollege.api;

import com.atcwl.gulicollege.dto.OrderUserDto;
import com.atcwl.gulicollege.result.Result;
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
@FeignClient(value = "service-ucenter")
@Component
public interface UcenterApi {
    @GetMapping("/ucenter/front/get-order-member")
    Result<OrderUserDto> getOrderMember(@RequestParam("memberId") String memberId);

    @GetMapping("/ucenter/front/get-daily-data")
    Result<Map<String, Integer>> getDailyData(@RequestParam String day);
}
