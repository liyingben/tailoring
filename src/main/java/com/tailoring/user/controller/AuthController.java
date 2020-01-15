package com.tailoring.user.controller;

import com.tailoring.user.common.ApiResponse;
import com.tailoring.user.common.Consts;
import com.tailoring.user.common.Status;
import com.tailoring.user.exception.SecurityException;
import com.tailoring.user.payload.LoginRequest;
import com.tailoring.user.util.JwtUtil;
import com.tailoring.user.vo.JwtResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <p>
 * 认证 Controller，包括用户注册，用户登录请求
 * </p>
 *
 * @package: com.tailoring.user.controller
 * @description: 认证 Controller，包括用户注册，用户登录请求
 * @author: ben
 * @date: Created in 2018-12-07 17:23
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@Api(value = "用户", tags = {"用户"})
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "登陆",tags = "PDA" , notes = "用户登陆")
    public ApiResponse login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmailOrPhone(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.createJWT(authentication, loginRequest.getRememberMe());
        //写session
        request.getSession().setAttribute(Consts.SESSION_KEY, jwt);

        return ApiResponse.ofSuccess(new JwtResponse(jwt));
    }

    @PostMapping("/logout")
    @ApiOperation(value = "登出",tags = "PDA" , notes = "用户退出")
    public ApiResponse logout(HttpServletRequest request) {
        try {
            // 设置JWT过期
            jwtUtil.invalidateJWT(request);
        } catch (SecurityException e) {
            throw new SecurityException(Status.UNAUTHORIZED);
        }
        return ApiResponse.ofStatus(Status.LOGOUT);
    }
}
