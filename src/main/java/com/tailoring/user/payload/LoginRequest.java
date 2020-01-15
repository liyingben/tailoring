package com.tailoring.user.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 登录请求参数
 * </p>
 *
 * @package: com.tailoring.user.payload
 * @description: 登录请求参数
 * @author: ben
 * @date: Created in 2018-12-10 15:52
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Data
public class LoginRequest {

    /**
     * 用户名或邮箱或手机号
     */
    @ApiModelProperty(value = "用户名", example = "zjc", required = true)
    @NotBlank(message = "用户名不能为空")
    private String usernameOrEmailOrPhone;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", example = "123456", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 记住我
     */
    private Boolean rememberMe = false;

}
