package com.atcwl.gulicollege.controller;


import com.atcwl.gulicollege.entity.CrmBanner;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author atcwl
 * @since 2023-02-01
 */
@Api(tags = "幻灯片管理")
@RestController
@RequestMapping("/crm-banner/admin")
@CrossOrigin
public class CrmBannerController {
    @Autowired
    private CrmBannerService crmBannerService;

//    CRUD
    @ApiOperation("banner提交")
    @PostMapping("/add-banner")
    public Result<String> addBanner(@RequestBody CrmBanner crmBanner) {
        boolean save = crmBannerService.save(crmBanner);
        if (!save)
            throw new GuliException(20001, "保存Banner信息失败");
        return Result.ok();
    }

    @ApiOperation("banner获取")
    @GetMapping("/get-banner/{id}")
    public Result<CrmBanner> getBanner(@PathVariable String id) {
        CrmBanner crmBanner = crmBannerService.getById(id);
        if (null == crmBanner)
            throw new GuliException(20001, "获取Banner信息失败");
        return Result.ok(crmBanner);
    }

    @ApiOperation("banner提交")
    @PutMapping("/update-banner")
    public Result<String> updateBanner(@RequestBody CrmBanner crmBanner) {
        boolean update = crmBannerService.updateById(crmBanner);
        if (!update)
            throw new GuliException(20001, "修改Banner信息失败");
        return Result.ok();
    }

    @ApiOperation("banner获取")
    @DeleteMapping("/remove-banner/{id}")
    public Result<String> removeBanner(@PathVariable String id) {
        boolean remove = crmBannerService.removeById(id);
        if (!remove)
            throw new GuliException(20001, "删除Banner信息失败");
        return Result.ok();
    }
}

