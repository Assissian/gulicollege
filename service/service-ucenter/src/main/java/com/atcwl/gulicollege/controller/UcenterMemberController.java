package com.atcwl.gulicollege.controller;


import com.atcwl.gulicollege.dto.OrderUserDto;
import com.atcwl.gulicollege.entity.UcenterMember;
import com.atcwl.gulicollege.entity.vo.UserLoginVo;
import com.atcwl.gulicollege.entity.vo.UserRegisterVo;
import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import com.atcwl.gulicollege.service.UcenterMemberService;
import com.atcwl.gulicollege.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author atcwl
 * @since 2023-02-02
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/ucenter/front")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserLoginVo user) {
        if (StringUtils.isEmpty(user.getPhone()) || StringUtils.isEmpty(user.getPassword()))
            throw new GuliException(20001, "用户名或密码不能为空");
        String token = ucenterMemberService.doLogin(user);
        return Result.ok(token);
    }

    @ApiOperation("用户注册接口")
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegisterVo registerVo) {
        if (StringUtils.isEmpty(registerVo.getNickname()) ||StringUtils.isEmpty(registerVo.getMobile()) ||
                StringUtils.isEmpty(registerVo.getPassword()) || StringUtils.isEmpty(registerVo.getCode()))
            throw new GuliException(20001, "请填写完整的信息");
        String token = ucenterMemberService.doRegister(registerVo);
        return Result.ok(token);
    }

    //根据token获取用户信息
    @GetMapping("/getMemberInfo")
    public Result<UcenterMember> getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return Result.ok(member);
    }

    @GetMapping("/get-order-member")
    public Result<OrderUserDto> getOrderMember(@RequestParam("memberId") String memberId) {
        UcenterMember byId = ucenterMemberService.getById(memberId);
        if (null == byId)
            throw new GuliException(20001, "创建订单时，获取用户信息失败");
        OrderUserDto orderUserDto = new OrderUserDto();
        BeanUtils.copyProperties(byId, orderUserDto);
        return Result.ok(orderUserDto);
    }

    @GetMapping("/get-daily-data")
    public Result<Map<String, Integer>> getDailyData(@RequestParam String day) {
        Map<String, Integer> map = ucenterMemberService.getDailyData(day);
        return Result.ok(map);
    }
}

