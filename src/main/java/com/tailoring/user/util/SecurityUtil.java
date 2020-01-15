package com.tailoring.user.util;

import cn.hutool.core.util.ObjectUtil;
import com.tailoring.user.common.Consts;
import com.tailoring.user.vo.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * Spring Security工具类
 * </p>
 *
 * @package: com.tailoring.user.util
 * @description: Spring Security工具类
 * @author: ben
 * @date: Created in 2018-12-12 18:30
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
public class SecurityUtil {
    /**
     * 获取当前登录用户用户名
     *
     * @return 当前登录用户用户名
     */
    public static String getCurrentUsername() {
        UserPrincipal currentUser = getCurrentUser();
        return ObjectUtil.isNull(currentUser) ? Consts.ANONYMOUS_NAME : currentUser.getUsername();
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息，匿名登录时，为null
     */
    public static UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if(authentication!=null) {
            Object userInfo = authentication.getPrincipal();
            if (userInfo instanceof UserDetails) {
                return (UserPrincipal) userInfo;
            }
        }
        return null;
    }
}
