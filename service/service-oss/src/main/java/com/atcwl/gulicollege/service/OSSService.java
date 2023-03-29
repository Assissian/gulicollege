package com.atcwl.gulicollege.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
public interface OSSService {
    String ossFileUpload(MultipartFile file);
}
