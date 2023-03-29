package com.atcwl.gulicollege.controller.front;

import com.atcwl.gulicollege.entity.CrmBanner;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author cwl
 * @date
 * @apiNote 前台使用的Banner接口
 */
@Api(tags = "前台幻灯片管理")
@RestController
@RequestMapping("/crm-banner/front")
@CrossOrigin
public class CrmBannerFrontController {
    @Autowired
    CrmBannerService crmBannerService;

    @ApiOperation("前台播放幻灯片获取")
    @GetMapping("/get-run-banner")
    public Result<List<CrmBanner>> getBanner() {
        List<CrmBanner> banners = crmBannerService.getAllBanner();
        return Result.ok(banners);
    }
}
