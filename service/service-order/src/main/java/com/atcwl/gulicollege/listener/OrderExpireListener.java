package com.atcwl.gulicollege.listener;

import com.atcwl.gulicollege.entity.Order;
import com.atcwl.gulicollege.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Component
public class OrderExpireListener {
    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = "${order.dead.queue}")
    public void orderExpireListen(String msg) {
        LambdaUpdateWrapper<Order> qw = new LambdaUpdateWrapper<Order>();
        qw.eq(Order::getOrderNo, msg);
        qw.set(Order::getStatus, 2);
        boolean update = orderService.update(qw);
    }
}
