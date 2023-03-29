package com.atcwl.gulicollege.service;

import com.atcwl.gulicollege.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author atcwl
 * @since 2023-02-03
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberId);

    Order getOrderByOrderNo(String orderNo);
}
