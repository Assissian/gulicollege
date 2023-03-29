package com.atcwl.gulicollege.controller;

import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.service.UcenterMemberService;
import com.atcwl.gulicollege.utils.HttpClientUtils;
import com.atcwl.gulicollege.utils.WenXinProperties;
import com.fasterxml.jackson.core.JsonFactoryBuilder;
import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Api(tags = "微信登录")
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WeiXinLoginController {
    private static final String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
            "?appid=%s" +
            "&redirect_uri=%s" +
            "&response_type=code" +
            "&scope=snsapi_login" +
            "&state=%s" +
            "#wechat_redirect";

    @Autowired
    private UcenterMemberService memberService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/login")
    public String getQrConnection(HttpSession session) {
        String redirectUrl = WenXinProperties.REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (Exception e) {
            throw new GuliException(20001, e.getMessage());
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：state
        //过期时间：30分钟
        String key = "wechar-open-state-" + session.getId();
        redisTemplate.opsForValue().set(key, state, 30, TimeUnit.MINUTES);
        String qrcodeUrl = String.format(
                baseUrl,
                WenXinProperties.APP_ID,
                redirectUrl,
                state);
        return "redirect:" + qrcodeUrl;
    }

    @GetMapping("/callback")
    public String callback(String code, String state, HttpSession session) {
        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问
        String key = "wechar-open-state-" + session.getId();
        String cacheState = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(cacheState))
            throw new GuliException(20001, "身份信息已经过期，请重新登录");
        if (!state.equals(cacheState))
            throw new GuliException(20001, "非法访问");
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                WenXinProperties.APP_ID,
                WenXinProperties.APP_SECRET,
                code
        );
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new GuliException(20001, e.getMessage());
        }
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(result, Map.class);

        String token = memberService.doWeiXinLogin(map);

        return "redirect:http://localhost:3000?token=" + token;
    }
}
