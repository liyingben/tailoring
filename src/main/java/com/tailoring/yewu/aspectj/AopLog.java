package com.tailoring.yewu.aspectj;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 使用 aop 切面记录请求日志信息
 * </p>
 *
 * @package: com.xkcoding.log.aop.aspectj
 * @description: 使用 aop 切面记录请求日志信息
 * @author: yangkai.shen
 * @date: Created in 2018/10/1 10:05 PM
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Aspect
@Component
@Slf4j
public class AopLog {
	private static final String START_TIME = "request-start";

	/**
	 * 切入点
	 */
	@Pointcut("execution(public * com.tailoring.yewu.controller.api.*Api.*(..))")
	public void log() {

	}

	/**
	 * 前置操作
	 *
	 * @param point 切入点
	 */
	@Before("log()")
	public void beforeLog(JoinPoint point) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

		log.info("【请求 URL】：{}", request.getRequestURL());
		log.info("【请求 IP】：{} 【请求方法】：{}", request.getRemoteAddr(),request.getMethod());
		log.info("【请求类名】：{}，【请求方法名】：{}", point.getSignature().getDeclaringTypeName(), point.getSignature().getName());

		Map<String, String[]> parameterMap = request.getParameterMap();

		//对象转化为Json
//		String json = null;
//		log.info("【请求参数 get】：{}，", JSONUtil.toJsonStr(parameterMap));
//		log.info("【请求参数 post】：{}，", JSONUtil.toJsonStr(point.getArgs()));
		ObjectMapper mapper = new ObjectMapper();
		try {
			log.info("【请求参数 get】：{}，", mapper.writeValueAsString(parameterMap));
			log.info("【请求参数 post】：{}，", mapper.writeValueAsString(point.getArgs()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		Long start = System.currentTimeMillis();
		request.setAttribute(START_TIME, start);
	}

	/**
	 * 环绕操作
	 *
	 * @param point 切入点
	 * @return 原方法返回值
	 * @throws Throwable 异常信息
	 */
	@Around("log()")
	public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
		Object result = point.proceed();
//		log.info("【返回值】：{}", JSONUtil.toJsonStr(result));
		ObjectMapper mapper = new ObjectMapper();
		try {
			log.info("【返回值】：{}",mapper.writeValueAsString(result));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 后置操作
	 */
	@AfterReturning("log()")
	public void afterReturning() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

		Long start = (Long) request.getAttribute(START_TIME);
		Long end = System.currentTimeMillis();
		log.info("【请求耗时】：{}毫秒", end - start);

		String header = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(header);
		log.info("【浏览器类型】：{}，【操作系统】：{}，【原始User-Agent】：{}", userAgent.getBrowser().toString(), userAgent.getOperatingSystem().toString(), header);
	}
}
