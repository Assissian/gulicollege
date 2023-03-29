package com.atcwl.gulicollege.service.impl;

import com.atcwl.gulicollege.entity.UcenterMember;
import com.atcwl.gulicollege.entity.vo.UserLoginVo;
import com.atcwl.gulicollege.entity.vo.UserRegisterVo;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.mapper.UcenterMemberMapper;
import com.atcwl.gulicollege.service.UcenterMemberService;
import com.atcwl.gulicollege.util.JwtUtils;
import com.atcwl.gulicollege.util.MD5;
import com.atcwl.gulicollege.utils.HttpClientUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author atcwl
 * @since 2023-02-02
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${user.default.avatar}")
    private String avatar;

    @Override
    public String doLogin(UserLoginVo user) {
        String phone = user.getPhone();
        String password = user.getPassword();
        String encrypt = MD5.encrypt(password);
        LambdaQueryWrapper<UcenterMember> qw = new LambdaQueryWrapper<>();
        qw.eq(UcenterMember::getMobile, phone);
        qw.eq(UcenterMember::getPassword, encrypt);
        UcenterMember ucenterMember = baseMapper.selectOne(qw);
        if (null == ucenterMember)
            throw new GuliException(30001, "用户名或者密码错误");
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return jwtToken;
    }

    @Override
    public String doRegister(UserRegisterVo registerVo) {
        String phone = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickname();
        String code = registerVo.getCode();

        //首先需要判断手机号是否已经被注册过
        LambdaQueryWrapper<UcenterMember> qw = new LambdaQueryWrapper<>();
        qw.eq(UcenterMember::getMobile, phone);
        UcenterMember ucenterMember = baseMapper.selectOne(qw);
        if (null != ucenterMember)
            throw new GuliException(20002, "此手机号已经注册过用户");

        //校验验证码
        String codeOf = redisTemplate.opsForValue().get(phone);
        if (StringUtils.isEmpty(codeOf))
            throw new GuliException(30002, "验证码已经过期，请重新发送");
        if (!codeOf.equals(code))
            throw new GuliException(30002, "验证码不正确");

        ucenterMember = new UcenterMember();
        ucenterMember.setMobile(phone);
        String encrypt = MD5.encrypt(password);
        ucenterMember.setPassword(encrypt);
        ucenterMember.setNickname(nickName);
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar(avatar);
        int insert = baseMapper.insert(ucenterMember);
        if (insert <= 0)
            throw new GuliException(30001, "注册用户失败");

        String userId = ucenterMember.getId();
        String jwtToken = JwtUtils.getJwtToken(userId, nickName);

        return jwtToken;
    }

    @Override
    public String doWeiXinLogin(Map<String, String> map) {
        String accessToken = map.get("access_token");
        String openId = map.get("openid");
        LambdaQueryWrapper<UcenterMember> qw = new LambdaQueryWrapper<>();
        qw.eq(UcenterMember::getOpenid, openId);
        UcenterMember ucenterMember = baseMapper.selectOne(qw);
        if (null == ucenterMember) {
            //这时是新用户注册，需要向微信服务器请求用户的一些基本信息
            //访问微信的资源服务器，获取用户信息
             String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                     "?access_token=%s" +
                     "&openid=%s";
             String userInfoUrl = String.format(
                     baseUserInfoUrl,
                     accessToken,
                     openId
                     );
             String result = null;
            try {
                result = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new GuliException(20001, e.getMessage());
            }
            Gson gson = new Gson();
            Map<String, String> resultMap = gson.fromJson(result, Map.class);
            String nickName = resultMap.get("nickname");
            String avatar = resultMap.get("headimgurl");

            ucenterMember = new UcenterMember();
            ucenterMember.setOpenid(openId);
            ucenterMember.setNickname(nickName);
            ucenterMember.setAvatar(avatar);
            int insert = baseMapper.insert(ucenterMember);
            if (insert <= 0)
                throw new GuliException(20001, "用户注册失败");
        }
        String userId = ucenterMember.getId();
        String nickname = ucenterMember.getNickname();
        String jwtToken = JwtUtils.getJwtToken(userId, nickname);
        return jwtToken;
    }

    @Override
    public Map<String, Integer> getDailyData(String day) {
        Map<String, Integer> map = new HashMap<>();
        Integer registerNo = baseMapper.selectRegisterUserNo(day);
        map.put("register_no", registerNo);
        Integer loginNo = RandomUtils.nextInt(100);
        map.put("login_no", loginNo);
        //这里实际还要查询用户的登录人数，但目前缺少统计该项的手段，需要加一个用户最后一次登录时间字段
        return map;
    }
}
