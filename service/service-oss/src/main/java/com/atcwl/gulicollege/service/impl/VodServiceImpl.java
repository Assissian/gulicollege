package com.atcwl.gulicollege.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.service.VodService;
import com.atcwl.gulicollege.utils.InitVodClient;
import com.atcwl.gulicollege.utils.OSSConfigureProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
                    //accessKeyId, accessKeySecret
                    //fileName：上传文件原始名称
                    // 01.03.09.mp4
                    String fileName = file.getOriginalFilename();
                    //title：上传之后显示名称
                    String title = fileName.substring(0, fileName.lastIndexOf("."));
                    //inputStream：上传文件输入流
                    InputStream inputStream = file.getInputStream();
                    UploadStreamRequest request = new UploadStreamRequest(OSSConfigureProperties.KEY_ID, OSSConfigureProperties.KEY_SECRET, title, fileName, inputStream);

                    UploadVideoImpl uploader = new UploadVideoImpl();
                    UploadStreamResponse response = uploader.uploadStream(request);

                    String videoId = null;
                    if (response.isSuccess()) {
                        videoId = response.getVideoId();
                    } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                        videoId = response.getVideoId();
                    }
                    return videoId;
                }catch(Exception e) {
                    e.printStackTrace();
                    return null;
                }
    }

    @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
                    //初始化对象
                    DefaultAcsClient client = InitVodClient.initVodClient(OSSConfigureProperties.KEY_ID, OSSConfigureProperties.KEY_SECRET);
                    //创建删除视频request对象
                    DeleteVideoRequest request = new DeleteVideoRequest();

                    //videoIdList值转换成 1,2,3
                    String videoIds = String.join(",", videoIdList);

                    //向request设置视频id
                    request.setVideoIds(videoIds);
                    //调用初始化对象的方法实现删除
                    client.getAcsResponse(request);
                }catch(Exception e) {
                    e.printStackTrace();
                    throw new GuliException(20001,"删除视频失败");
                }
    }

    @Override
    public String getPlayAuthById(String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(OSSConfigureProperties.KEY_ID, OSSConfigureProperties.KEY_SECRET);
            //创建获取视频权限request对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse acsResponse = client.getAcsResponse(request);
            String playAuth = acsResponse.getPlayAuth();
            return playAuth;
        } catch (Exception e) {
            throw new GuliException(30001, e.getMessage());
        }
    }
}
