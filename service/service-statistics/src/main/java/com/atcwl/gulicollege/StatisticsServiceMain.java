package com.atcwl.gulicollege;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@SpringBootApplication
@EnableFeignClients
public class StatisticsServiceMain {
    @Value(
            "${spring.main.log-startup-info}"
    )
    private String s;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(StatisticsServiceMain.class, args);
        StatisticsServiceMain bean = run.getBean(StatisticsServiceMain.class);
        System.out.println(bean.s);

    }
}
