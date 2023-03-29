package com.atcwl.gulicollege.service;

import com.atcwl.gulicollege.entity.UcenterMember;
import com.atcwl.gulicollege.entity.vo.UserLoginVo;
import com.atcwl.gulicollege.entity.vo.UserRegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author atcwl
 * @since 2023-02-02
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String doLogin(UserLoginVo user);

    String doRegister(UserRegisterVo registerVo);

    String doWeiXinLogin(Map<String, String> map);

    Map<String, Integer> getDailyData(String day);
}
