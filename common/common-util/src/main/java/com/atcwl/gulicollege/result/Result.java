package com.atcwl.gulicollege.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全局统一返回结果类
 * 这里对前端会有的一个参数做说明：
 * 前端在返回Result对象是会多附带一个属性：ok
 * 这个是因为SpringMVC封装对应的pojo对象时对方法名解析的问题，
 * 因为本类中有一个isOk的方法，而SpringMVC在解析方法时，默认boolean属性
 * 的getter方法为is开头，因此将ok解释为一个boolean类型的成员变量
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public Result(){}

    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> build(Integer code, String message) {
        Result<T> result = build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static<T> Result<T> ok(){
        return Result.ok(null);
    }

    /**
     * 操作成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> ok(T data){
        //Result<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> fail(T data){
        //Result<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    //返回this的对象是为了方便链式调用编程，在接受到返回对象之后可以继续调用方法完成对于对象的完善
    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if(this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }

    /**
     * 将if-else判断直接封装为Result类的一个方法
     * 因为在controller那边会多次写这样的if-else判断，因此直接封装
     * @param flag 操作成功与否
     * @param <T> 根据controller的方法返回值类型确定
     * @return 返回成功/失败的Result对象
     */
    public static <T> Result<T> status(boolean flag) {
        return flag ? ok() : fail();
    }
}
