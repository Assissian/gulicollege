package com.atcwl.gulicollege.service;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
public interface SmsService {
    boolean send(String phone, String code);
}
