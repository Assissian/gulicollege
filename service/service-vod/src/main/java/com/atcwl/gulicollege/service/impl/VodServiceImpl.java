package com.atcwl.gulicollege.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atcwl.gulicollege.Utils.InitVodCilent;
import com.atcwl.gulicollege.service.VodService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
public class VodServiceImpl {
    //@Override
    //public String uploadVideoAly(MultipartFile file) {
    //
    //    try {
    //        //accessKeyId, accessKeySecret
    //        //fileName：上传文件原始名称
    //        // 01.03.09.mp4
    //        String fileName = file.getOriginalFilename();
    //        //title：上传之后显示名称
    //        String title = fileName.substring(0, fileName.lastIndexOf("."));
    //        //inputStream：上传文件输入流
    //        InputStream inputStream = file.getInputStream();
    //        UploadStreamRequest request = new UploadStreamRequest("LTAI5tJofv2fwVBJiQ9T3Hit", "xJ7VhSjMHU6S1Muhvb7AuQe7AJgG08", title, fileName, inputStream);
    //
    //        UploadVideoImpl uploader = new UploadVideoImpl();
    //        UploadStreamResponse response = uploader.uploadStream(request);
    //
    //        String videoId = null;
    //        if (response.isSuccess()) {
    //            videoId = response.getVideoId();
    //        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
    //            videoId = response.getVideoId();
    //        }
    //        return videoId;
    //    }catch(Exception e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //
    //}
    //
    //@Override
    //public void removeMoreAlyVideo(List videoIdList) {
    //    try {
    //        //初始化对象
    //        DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
    //        //创建删除视频request对象
    //        DeleteVideoRequest request = new DeleteVideoRequest();
    //
    //        //videoIdList值转换成 1,2,3
    //        String videoIds = String.join(",", videoIdList);
    //
    //        //向request设置视频id
    //        request.setVideoIds(videoIds);
    //        //调用初始化对象的方法实现删除
    //        client.getAcsResponse(request);
    //    }catch(Exception e) {
    //        e.printStackTrace();
    //        throw new GuliException(20001,"删除视频失败");
    //    }
    //}

    public static void main(String[] args) throws ClientException, FileNotFoundException {
        //File file = new File("F:\\studying\\learn-projects\\谷粒学院\\资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4");
        //InputStream inputStream = new FileInputStream(file);
        //UploadStreamRequest request = new UploadStreamRequest("LTAI5tJofv2fwVBJiQ9T3Hit", "xJ7VhSjMHU6S1Muhvb7AuQe7AJgG08", "title", "6 - What If I Want to Move Faster.mp4", inputStream);
        //
        //UploadVideoImpl uploader = new UploadVideoImpl();
        //UploadStreamResponse response = uploader.uploadStream(request);
        //String videoId = null;
        //if (response.isSuccess()) {
        //    videoId = response.getVideoId();
        //} else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
        //    videoId = response.getVideoId();
        //}
        //System.out.println(videoId);
        try {
                    //初始化对象
                    DefaultAcsClient client = InitVodCilent.initVodClient("LTAI5tJofv2fwVBJiQ9T3Hit", "xJ7VhSjMHU6S1Muhvb7AuQe7AJgG08");
                    //创建删除视频request对象
                    DeleteVideoRequest request = new DeleteVideoRequest();

                    //videoIdList值转换成 1,2,3
                    //String videoIds = String.join(",", videoIdList);

                    //向request设置视频id
                    request.setVideoIds("4b4c65b0a13571ed802e0675a0ec0102");
                    //调用初始化对象的方法实现删除
                    client.getAcsResponse(request);
                }catch(Exception e) {
                    e.printStackTrace();
                    //throw new GuliException(20001,"删除视频失败");
                }
    }
}