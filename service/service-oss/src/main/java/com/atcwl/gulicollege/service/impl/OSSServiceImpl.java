package com.atcwl.gulicollege.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.service.OSSService;
import com.atcwl.gulicollege.utils.OSSConfigureProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Service
public class OSSServiceImpl implements OSSService {
    @Override
    public String ossFileUpload(MultipartFile file) {
        String endpoint = OSSConfigureProperties.END_POINT;
        String keyId = OSSConfigureProperties.KEY_ID;
        String keySecret = OSSConfigureProperties.KEY_SECRET;
        String bucketName = OSSConfigureProperties.BUCKET_NAME;

        String dateStr = new DateTime().toString("yyyy/MM/dd");
        String fileName = "gulicollege/" + dateStr + "/" + System.currentTimeMillis() + file.getOriginalFilename();
        OSS ossClient = null;
        try {
            ossClient = new OSSClientBuilder().build(endpoint, keyId, keySecret);
            InputStream fileInputStream = file.getInputStream();
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, fileInputStream);
            Date date = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30);
            String urlStr = ossClient.generatePresignedUrl(bucketName, fileName, date).toString();
            String url = urlStr.substring(0, urlStr.indexOf("?"));
            return url;
        } catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20002, "上传文件失败！");
        } finally {
            if(ossClient != null)
                ossClient.shutdown();
        }
    }
}
