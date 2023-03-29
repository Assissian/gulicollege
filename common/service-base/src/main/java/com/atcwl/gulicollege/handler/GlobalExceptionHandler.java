package com.atcwl.gulicollege.handler;

import com.atcwl.gulicollege.exception.GuliException;
import com.atcwl.gulicollege.result.Result;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<String> error(Exception e) {
        return Result.fail(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(GuliException.class)
    public Result<String> guliError(GuliException e) {
        return Result.build(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Map<String, Object>> methodArgError(MethodArgumentNotValidException e) {
        // 获取所有的错误
        // 获取错误提示
        // 获取错误字段
        // 将所有的错误提示使用";"拼接起来并返回
        StringJoiner sj = new StringJoiner(";");
        e.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        // 此处通常定义一个全局相应对象返回
        Map<String, Object> map = new HashMap<>();
        // 此处状态码可以通过枚举或者常量定义
        map.put("code", 1001);
        map.put("msg", sj.toString());
        return Result.fail(map);
    }
}
