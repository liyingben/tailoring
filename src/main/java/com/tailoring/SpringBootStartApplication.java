package com.tailoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cache.annotation.EnableCaching;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * <p>
 * 启动类
 * </p>
 *
 * @package: com.tailoring.yewu
 * @description: 启动类
 * @author: ben
 * @date: Created in 2018/10/28 22:58
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@SpringBootApplication
@EnableCaching
public class SpringBootStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootStartApplication.class, args);
    }
}
