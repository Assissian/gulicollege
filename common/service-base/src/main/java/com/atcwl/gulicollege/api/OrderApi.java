package com.atcwl.gulicollege.api;

import com.atcwl.gulicollege.result.Result;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@FeignClient("service-order")
@Component
public interface OrderApi {
    @GetMapping("/service-order/order-pay-log/query-buy-course")
    Result<Boolean> queryBuyCourse(@RequestParam String courseId, @RequestParam String memberId);
}
