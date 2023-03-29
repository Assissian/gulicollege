package com.atcwl.gulicollege.service.impl;

import com.atcwl.gulicollege.entity.Order;
import com.atcwl.gulicollege.entity.PayLog;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.mapper.PayLogMapper;
import com.atcwl.gulicollege.service.OrderService;
import com.atcwl.gulicollege.service.PayLogService;
import com.atcwl.gulicollege.utils.HttpClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author atcwl
 * @since 2023-02-03
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;

    @Override
    public Map<String, Object> createNative(String orderNo) {
        try {
            LambdaQueryWrapper<Order> qw = new LambdaQueryWrapper<>();
            qw.eq(Order::getOrderNo, orderNo);
            Order order = orderService.getOne(qw);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmsss");
            String format = simpleDateFormat.format(new Date(new Date().getTime() + 2 * 60 * 1000L));
            Map<String, String> m = new HashMap<>();
            //1、设置支付参数
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle());
            m.put("out_trade_no", orderNo);
            m.put("total_fee", order.getTotalFee().multiply(new
                 BigDecimal("100")).longValue()+"");
            m.put("time_expire", format);
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url",
                 "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");
                        //2、HTTPClient来根据URL访问第三方接口并且传递参数
            HttpClient client = new
                 HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
                        //client设置参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m,
                 "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4、封装返回结果集

            Map<String, Object> map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120,TimeUnit.MINUTES);

            return map;
        } catch (Exception e) {
            throw new GuliException(20001, e.getMessage());
        }
    }

    @Override
    public Map<String, String> queryPayingState(String orderNo) {
        try {
            //1、封装参数
            Map<String, String> m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new
                 HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,
                 "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            return resultMap;
        } catch (Exception e) {
            throw new GuliException(20001, e.getMessage());
        }
    }

    @Override
    public Boolean queryIsBuyCourse(String courseId, String memberId) {
        LambdaQueryWrapper<Order> qw = new LambdaQueryWrapper<>();
        qw.eq(Order::getCourseId, courseId);
        qw.eq(Order::getMemberId, memberId);
        qw.eq(Order::getStatus, 1);
        qw.select(Order::getOrderNo);
        Order order = orderService.getOne(qw);
        if (null == order)
            //throw new GuliException(20001, "获取订单信息异常");
            return false;
        LambdaQueryWrapper<PayLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PayLog::getOrderNo, order.getOrderNo());
        wrapper.eq(PayLog::getTradeState, "SUCCESS");
        Long count = baseMapper.selectCount(wrapper);
        if (count <= 0)
            return false;
        else
            return true;
    }

    @Transactional
    @Override
    public void addNewPayLog(String orderNo, Map<String, String> result) {
        Order order = orderService.getOrderByOrderNo(orderNo);
        order.setPayType(1);
        order.setStatus(1);
        boolean update = orderService.updateById(order);
        if (!update)
            throw new GuliException(20001, "更新订单状态失败");
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setTotalFee(order.getTotalFee());
        payLog.setTransactionId(result.get("transaction_id"));
        payLog.setTradeState(result.get("trade_state"));
        payLog.setPayType(1);
        int insert = baseMapper.insert(payLog);
        if (insert <= 0)
            throw new GuliException(20001, "更新支付记录失败");
    }
}
