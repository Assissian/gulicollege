package com.atcwl.gulicollege.service;

import com.atcwl.gulicollege.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author atcwl
 * @since 2023-02-03
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createNative(String orderNo);

    Map<String, String> queryPayingState(String orderNo);

    Boolean queryIsBuyCourse(String courseId, String memberId);

    void addNewPayLog(String orderNo, Map<String, String> result);
}
