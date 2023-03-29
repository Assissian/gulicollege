package com.atcwl.gulicollege.controller;

import com.atcwl.gulicollege.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Api(tags = "管理员登录")
@CrossOrigin
@RestController
@RequestMapping("/gulicollege/user")
public class EduLoginController {
    @PostMapping("/login")
    public Result login() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", "admin-token");
        return Result.ok(map);
    }

    @GetMapping("/info")
    public Result info() {
        HashMap<String, String> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("name", "admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }
}
