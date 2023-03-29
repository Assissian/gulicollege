package com.atcwl.gulicollege.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Data
public class UserLoginVo {
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "密码")
    private String password;
}
