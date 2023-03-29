package com.atcwl.gulicollege.controller;


import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author atcwl
 * @since 2023-02-03
 */
@RestController
@RequestMapping("/service-order/order-pay-log")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    @GetMapping("/create-native/{orderNo}")
    public Result<Map<String, Object>> createNative(@PathVariable String orderNo) {
        Map<String, Object> result = payLogService.createNative(orderNo);
        return Result.ok(result);
    }

    @GetMapping("/query-paying-state/{orderNo}")
    public Result<Map<String, String>> queryPayingState(@PathVariable String orderNo) {
        Map<String, String> result = payLogService.queryPayingState(orderNo);
        String tradeState = result.get("trade_state");
        if ("SUCCESS".equals(tradeState)) {
            payLogService.addNewPayLog(orderNo, result);
        }
        return Result.ok(result);
    }

    @GetMapping("/query-buy-course")
    public Result<Boolean> queryBuyCourse(@RequestParam String courseId, @RequestParam String memberId) {
        Boolean result = payLogService.queryIsBuyCourse(courseId, memberId);
        return Result.ok(result);
    }
}

