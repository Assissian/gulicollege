package com.atcwl.gulicollege.service.impl;

import com.atcwl.gulicollege.api.EduApi;
import com.atcwl.gulicollege.api.UcenterApi;
import com.atcwl.gulicollege.dto.OrderCourseDto;
import com.atcwl.gulicollege.dto.OrderUserDto;
import com.atcwl.gulicollege.entity.Order;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.mapper.OrderMapper;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.OrderService;
import com.atcwl.gulicollege.util.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author atcwl
 * @since 2023-02-03
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduApi eduApi;

    @Autowired
    private UcenterApi ucenterApi;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${order.delay.exchange}")
    private String orderExchange;

    @Override
    public String createOrder(String courseId, String memberId) {
        Result<OrderCourseDto> courseById = eduApi.getCourseById(courseId);
        if (courseById.getCode() != 20000)
            throw new GuliException(20001, "创建订单失败");
        OrderCourseDto course = courseById.getData();

        Result<OrderUserDto> orderMember = ucenterApi.getOrderMember(memberId);
        if (orderMember.getCode() != 20000)
            throw new GuliException(20001, "创建订单失败");
        OrderUserDto userInfo = orderMember.getData();

        Order order = new Order();
        order.setOrderNo(IdWorker.getIdStr());
        order.setCourseId(courseId);
        order.setCourseTitle(course.getTitle());
        order.setCourseCover(course.getCover());
        order.setTeacherName(course.getTeacherName());
        order.setTotalFee(course.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfo.getMobile());
        order.setNickname(userInfo.getNickname());
        order.setStatus(0);
        try {
            int insert = baseMapper.insert(order);
            if (insert <= 0)
                 throw new GuliException(20001, "创建订单失败");
            sendOrderDelayMsg(order.getOrderNo(), "10000");
        } catch (Exception e) {
            throw new GuliException(20001, "请勿重复提交订单");
        }
        return order.getId();
    }

    @Override
    public Order getOrderByOrderNo(String orderNo) {
        LambdaQueryWrapper<Order> qw = new LambdaQueryWrapper<>();
        qw.eq(Order::getOrderNo, orderNo);
        Order order = baseMapper.selectOne(qw);
        if (null == order)
            throw new GuliException(20001, "获取订单信息失败");
        return order;
    }

    private void sendOrderDelayMsg(String orderId, String timeout){
        try {
            rabbitTemplate.convertAndSend(orderExchange, "", orderId, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setExpiration(timeout);
                    return message;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
