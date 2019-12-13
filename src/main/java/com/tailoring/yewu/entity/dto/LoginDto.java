package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {
    @ApiModelProperty(required = true, value = "用户名", example = "admin")
    private String username;
    @ApiModelProperty(required = true, value = "密码", example = "admin")
    private String password;
}
