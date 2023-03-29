package com.atcwl.gulicollege.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Configuration
@EnableRabbit
public class MqConfig {
    @Value("${order.delay.queue}")
    private String orderTtlQueue;
    @Value("${order.delay.exchange}")
    private String orderTtlExchange;
    @Value("${order.dead.queue}")
    private String orderDeadQueue;
    @Value("${order.dead.exchange}")
    private String orderDeadExchange;
    @Value("${order.dead.routing_key}")
    private String orderDeadKey;


    @Bean
    public Queue orderQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", orderDeadExchange);
        args.put("x-dead-letter-routing-key", orderDeadKey);
        return new Queue(orderTtlQueue, true, false, false, args);
    }

    @Bean
    public FanoutExchange orderExchange() {
        return new FanoutExchange(orderTtlExchange, true, false, null);
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, FanoutExchange orderExchange) {
        return BindingBuilder.bind(orderQueue).to(orderExchange);
    }

    @Bean
    public Queue orderDeadLetterQueue() {
        return new Queue(orderDeadQueue, true);
    }

    @Bean
    public DirectExchange orderDeadLetterExchange() {
        return new DirectExchange(orderDeadExchange, true, false);
    }

    @Bean
    public Binding orderDeadLetterBinding() {
        return BindingBuilder.bind(orderDeadLetterQueue()).to(orderDeadLetterExchange()).with(orderDeadKey);
    }
}
