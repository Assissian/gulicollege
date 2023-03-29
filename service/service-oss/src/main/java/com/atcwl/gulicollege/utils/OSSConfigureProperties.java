package com.atcwl.gulicollege.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Component
@ConfigurationProperties("aliyun.oss")
@Data
public class OSSConfigureProperties implements InitializingBean {
    private String endpoint;
    private String keyId;
    private String keySecret;
    private String bucketName;

    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = this.endpoint;
        KEY_ID = this.keyId;
        KEY_SECRET = this.keySecret;
        BUCKET_NAME = this.bucketName;
    }
}
