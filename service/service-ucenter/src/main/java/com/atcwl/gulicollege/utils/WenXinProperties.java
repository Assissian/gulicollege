package com.atcwl.gulicollege.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author cwl
 * @date
 * @apiNote @PropertySource注解默认情况下只能解析xml和properties类型的文件
 *                  如果想要解析其他类型的文件，需要自定义PropertySourceFactory
 */
//@PropertySource(value = "classpath: application.yml")
@ConfigurationProperties(prefix = "weixin.open")
@Component
@Data
public class WenXinProperties implements InitializingBean {
    //@Value("${weixin.open.app_id}")
    private String appId;
    private String appSecret;
    private String redirectUrl;

    public static String APP_ID;
    public static String APP_SECRET;
    public static String REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appId;
        APP_SECRET = appSecret;
        REDIRECT_URL = redirectUrl;
    }
}
