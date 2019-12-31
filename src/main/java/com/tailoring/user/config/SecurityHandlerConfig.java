package com.tailoring.user.config;

import com.tailoring.user.common.Status;
import com.tailoring.user.util.ResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * <p>
 * Security 结果处理配置
 * </p>
 *
 * @package: com.tailoring.user.config
 * @description: Security 结果处理配置
 * @author: ben
 * @date: Created in 2018-12-07 17:31
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Configuration
public class SecurityHandlerConfig {

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> ResponseUtil.renderJson(response, Status.ACCESS_DENIED, null);
    }

}
