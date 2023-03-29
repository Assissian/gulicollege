package com.atcwl.gulicollege.controller;

import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.SmsService;
import com.atcwl.gulicollege.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Api(tags = "短信服务管理")
@RestController
@RequestMapping("/gulicollege/sms-service")
@CrossOrigin
public class SmsController {
    @Autowired
    private SmsService smsService;

    @ApiOperation("发送短信接口")
    @GetMapping("/send-msg/{phone}")
    public Result<String> sendMessage(@PathVariable String phone) {
        boolean send = smsService.send(phone, RandomUtil.getFourBitRandom());
        return send ? Result.ok() : Result.fail();
    }
}
