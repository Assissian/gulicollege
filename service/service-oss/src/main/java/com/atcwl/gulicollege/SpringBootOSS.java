package com.atcwl.gulicollege;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


/**
 * @Author cwl
 * @date
 * @apiNote
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringBootOSS {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootOSS.class, args);
    }
}
