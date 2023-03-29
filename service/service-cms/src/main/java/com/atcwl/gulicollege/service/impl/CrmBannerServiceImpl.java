package com.atcwl.gulicollege.service.impl;

import com.atcwl.gulicollege.entity.CrmBanner;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.mapper.CrmBannerMapper;
import com.atcwl.gulicollege.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author atcwl
 * @since 2023-02-01
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    @Override
    public List<CrmBanner> getAllBanner() {
        QueryWrapper<CrmBanner> qw = new QueryWrapper<>();
        qw.last("limit 2");
        List<CrmBanner> crmBanners = baseMapper.selectList(qw);
        if (null == crmBanners)
            throw new GuliException(20001, "获取Banner信息失败");
        return crmBanners;
    }
}
