package com.tailoring.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * 自定义配置
 * </p>
 *
 * @package: com.tailoring.user.config
 * @description: 自定义配置
 * @author: ben
 * @date: Created in 2018-12-13 10:56
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@ConfigurationProperties(prefix = "custom.config")
@Data
public class CustomConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;
}
