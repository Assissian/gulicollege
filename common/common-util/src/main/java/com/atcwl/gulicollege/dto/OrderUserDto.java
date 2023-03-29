package com.atcwl.gulicollege.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cwl
 * @date
 * @apiNote
 */
@Data
public class OrderUserDto {
    @ApiModelProperty(value = "会员id")
    private String id;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "会员手机")
    private String mobile;
}
