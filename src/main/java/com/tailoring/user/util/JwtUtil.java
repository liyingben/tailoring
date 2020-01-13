package com.tailoring.user.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.tailoring.user.common.Consts;
import com.tailoring.user.common.Status;
import com.tailoring.user.config.JwtConfig;
import com.tailoring.user.exception.SecurityException;
import com.tailoring.user.vo.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;

//import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * <p>
 * JWT 工具类
 * </p>
 *
 * @package: com.tailoring.user.util
 * @description: JWT 工具类
 * @author: ben
 * @date: Created in 2018-12-07 13:42
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@EnableConfigurationProperties(JwtConfig.class)
@Configuration
@Slf4j
public class JwtUtil {
    @Autowired
    private JwtConfig jwtConfig;

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    private CacheManager cacheManager;

    /**
     * 创建JWT
     *
     * @param rememberMe  记住我
     * @param id          用户id
     * @param subject     用户名
     * @param roles       用户角色
     * @param authorities 用户权限
     * @return JWT
     */
    public String createJWT(Boolean rememberMe, Long id, String subject, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id.toString())
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getKey())
                .claim("roles", roles)
                .claim("authorities", authorities);

        // 设置过期时间
        Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getTtl();
        if (ttl > 0) {
            builder.setExpiration(DateUtil.offsetMillisecond(now, ttl.intValue()));
        }

        String jwt = builder.compact();
        // 将生成的JWT保存至Redis
//        stringRedisTemplate.opsForValue()
//                .set(Consts.REDIS_JWT_KEY_PREFIX + subject, jwt, ttl, TimeUnit.MILLISECONDS);
        EhcacheUtil.getInstance().put("user", Consts.REDIS_JWT_KEY_PREFIX + subject, jwt);
        return jwt;
    }

    /**
     * 创建JWT
     *
     * @param authentication 用户认证信息
     * @param rememberMe     记住我
     * @return JWT
     */
    public String createJWT(Authentication authentication, Boolean rememberMe) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return createJWT(rememberMe, userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getRoles(), userPrincipal.getAuthorities());
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getKey())
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();
            String redisKey = Consts.REDIS_JWT_KEY_PREFIX + username;

            // 校验redis中的JWT是否存在
//            cacheManager.cacheExists(redisKey);
            if (EhcacheUtil.getInstance().get("user", redisKey) == null) {
                throw new SecurityException(Status.TOKEN_EXPIRED);
            }
//            Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
//            if (Objects.isNull(expire) || expire <= 0) {
//                throw new SecurityException(Status.TOKEN_EXPIRED);
//            }

            // 校验redis中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            String redisToken = EhcacheUtil.getInstance().get("user", redisKey).toString();
//            String redisToken = stringRedisTemplate.opsForValue()
//                    .get(redisKey);
            if (!StrUtil.equals(jwt, redisToken)) {
                throw new SecurityException(Status.TOKEN_OUT_OF_CTRL);
            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new SecurityException(Status.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new SecurityException(Status.TOKEN_PARSE_ERROR);
        }
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // 从redis中清除JWT
        EhcacheUtil.getInstance().remove("user", Consts.REDIS_JWT_KEY_PREFIX + username);
//        stringRedisTemplate.delete(Consts.REDIS_JWT_KEY_PREFIX + username);
    }

    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }

    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        bearerToken = request.getParameter("token");
        if (StrUtil.isNotBlank(bearerToken) ) {
            return bearerToken;
        }
        if (request.getSession().getAttribute(Consts.SESSION_KEY) != null) {
            return request.getSession().getAttribute(Consts.SESSION_KEY).toString();
        } else {
            return null;
        }
    }



}
