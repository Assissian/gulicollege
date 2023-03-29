package com.atcwl.gulicollege.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
public interface VodService {
    String uploadVideoAly(MultipartFile file);
    void removeMoreAlyVideo(List videoIdList);

    String getPlayAuthById(String id);
}
