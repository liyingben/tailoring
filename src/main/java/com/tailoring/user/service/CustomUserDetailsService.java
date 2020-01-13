package com.tailoring.user.service;

import com.tailoring.user.model.Group;
import com.tailoring.user.model.Permission;
import com.tailoring.user.model.Role;
import com.tailoring.user.model.User;
import com.tailoring.user.repository.GroupDao;
import com.tailoring.user.repository.PermissionDao;
import com.tailoring.user.repository.RoleDao;
import com.tailoring.user.repository.UserDao;
import com.tailoring.user.vo.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 自定义UserDetails查询
 * </p>
 *
 * @package: com.tailoring.user.service
 * @description: 自定义UserDetails查询
 * @author: ben
 * @date: Created in 2018-12-10 10:29
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private GroupDao groupDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) throws UsernameNotFoundException {
        User user = userDao.findByUsernameOrEmailOrPhone(usernameOrEmailOrPhone, usernameOrEmailOrPhone, usernameOrEmailOrPhone)
                .orElseThrow(() -> new UsernameNotFoundException("未找到用户信息 : " + usernameOrEmailOrPhone));
        List<Role> roles = roleDao.selectByUserId(user.getId());
        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
        List<Permission> permissions = permissionDao.selectByRoleIdList(roleIds);

        List<Group> groups = groupDao.selectByUserId(user.getId());

        return UserPrincipal.create(user, roles, permissions,groups);
    }
}
