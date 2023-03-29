package com.atcwl.gulicollege;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@SpringBootApplication
@EnableFeignClients
public class UcenterMain {
    public static void main(String[] args) {
        SpringApplication.run(UcenterMain.class, args);
    }
}
