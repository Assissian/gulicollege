package com.atcwl.gulicollege.controller;

import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.OSSService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Api(tags = "OSS上传文件")
@RestController
@RequestMapping("/gulicollege/oss-service")
@CrossOrigin
public class OSSController {
    @Autowired
    private OSSService ossService;

    @PostMapping("/upload")
    public Result<String> uploadFile(MultipartFile file) {
        String url = ossService.ossFileUpload(file);
        return Result.ok(url);
    }
}
