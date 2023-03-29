package com.atcwl.gulicollege.controller;


import com.atcwl.gulicollege.entity.Order;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.OrderService;
import com.atcwl.gulicollege.util.JwtUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author atcwl
 * @since 2023-02-03
 */
@ApiOperation("订单管理")
@RestController
@RequestMapping("/service-order/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/submit-order-req/{courseId}")
    public Result<String> submitOrderReq(@PathVariable String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderId = orderService.createOrder(courseId, memberId);
        return Result.ok(orderId);
    }

    @GetMapping("/get-order-info/{orderId}")
    public Result<Order> getOrderInfo(@PathVariable String orderId) {
        Order order = orderService.getById(orderId);
        if (null == order)
            throw new GuliException(20001, "无法获取订单信息");
        return Result.ok(order);
    }
}

