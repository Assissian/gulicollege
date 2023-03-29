package com.atcwl.gulicollege;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Locale;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@SpringBootApplication
@EnableFeignClients
public class SpringBootMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringBootMain.class, args);
        System.out.println(run.getMessage("hi", null, Locale.CHINA));
        System.out.println(run.getMessage("hi", null, Locale.ENGLISH));
        System.out.println(run);
    }
}
