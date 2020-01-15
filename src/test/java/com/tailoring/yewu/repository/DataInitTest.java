package com.tailoring.yewu.repository;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;

import com.tailoring.user.model.*;
import com.tailoring.user.model.unionkey.RolePermissionKey;
import com.tailoring.user.model.unionkey.UserRoleKey;
import com.tailoring.user.repository.*;
import com.tailoring.yewu.SpringBootStartApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * 数据初始化测试
 * </p>
 *
 * @package: com.xkcoding.rbac.security.repository
 * @description: 数据初始化测试
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 11:26
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
public class DataInitTest extends SpringBootStartApplicationTests {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    public void initTest() {
        init();
    }

    private void init() {
        User admin = createUser(true);
        User user = createUser(false);

        Role roleAdmin = createRole(true);
        Role roleUser = createRole(false);

        createUserRoleRelation(admin.getId(), roleAdmin.getId());
        createUserRoleRelation(user.getId(), roleUser.getId());

        // 管理员权限
        Permission adminApiPostPerm = createPermission("/api/**", "管理员api", 2, "btn:api", "POST", 1, 0L);
        Permission adminApiGetPerm = createPermission("/api/**", "管理员api", 2, "btn:api", "Get", 1, 0L);
        Permission adminPagePerm = createPermission("/admin/**", "管理员api", 2, "page:admin", "Get", 1, 0L);
        // 按钮权限

         Permission userBtnQueryPerm = createPermission("/api/tailoringTask/**", "PDA接口", 2, "btn:api:tailoringPlans", "POST", 1, 0L);
         Permission userBtnQueryPerm0 = createPermission("/tailoringPlans/**", "PDA接口", 2, "btn:tailoringPlans", "GET", 1, 0L);
         Permission userBtnQueryPerm1 = createPermission("/tailoring/**", "PDA接口", 2, "btn:tailoring", "POST", 1, 0L);
         Permission userBtnQueryPerm2 = createPermission("/api/auth/logout", "PDA接口", 2, "btn:auth:logout", "POST", 1, 0L);
         Permission userBtnQueryPerm3 = createPermission("/tailoring/**", "PDA接口", 2, "btn:tailoring", "GET", 1, 0L);


        createRolePermissionRelation(roleAdmin.getId(), adminApiPostPerm.getId());
        createRolePermissionRelation(roleAdmin.getId(), adminApiGetPerm.getId());
        createRolePermissionRelation(roleAdmin.getId(), adminPagePerm.getId());


        createRolePermissionRelation(roleUser.getId(), userBtnQueryPerm.getId());
        createRolePermissionRelation(roleUser.getId(), userBtnQueryPerm0.getId());
        createRolePermissionRelation(roleUser.getId(), userBtnQueryPerm1.getId());
        createRolePermissionRelation(roleUser.getId(), userBtnQueryPerm2.getId());
        createRolePermissionRelation(roleUser.getId(), userBtnQueryPerm3.getId());
    }

    private void createRolePermissionRelation(Long roleId, Long permissionId) {
        RolePermission adminPage = new RolePermission();
        RolePermissionKey adminPageKey = new RolePermissionKey();
        adminPageKey.setRoleId(roleId);
        adminPageKey.setPermissionId(permissionId);
        adminPage.setId(adminPageKey);
        rolePermissionDao.save(adminPage);
    }

    private Permission createPermission(String url, String name, Integer type, String permission, String method, Integer sort, Long parentId) {
        Permission perm = new Permission();
        perm.setId(snowflake.nextId());
        perm.setUrl(url);
        perm.setName(name);
        perm.setType(type);
        perm.setPermission(permission);
        perm.setMethod(method);
        perm.setSort(sort);
        perm.setParentId(parentId);
        permissionDao.save(perm);
        return perm;
    }

    private void createUserRoleRelation(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        UserRoleKey key = new UserRoleKey();
        key.setUserId(userId);
        key.setRoleId(roleId);
        userRole.setId(key);
        userRoleDao.save(userRole);
    }

    private Role createRole(boolean isAdmin) {
        Role role = new Role();
        role.setId(snowflake.nextId());
        role.setName(isAdmin ? "管理员" : "普通用户");
        role.setDescription(isAdmin ? "超级管理员" : "普通用户");
        role.setCreateTime(DateUtil.current(false));
        role.setUpdateTime(DateUtil.current(false));
        roleDao.save(role);
        return role;
    }

    private User createUser(boolean isAdmin) {
        User user = new User();
        user.setId(snowflake.nextId());
        user.setUsername(isAdmin ? "admin" : "user");
        user.setNickname(isAdmin ? "管理员" : "普通用户");
        user.setPassword(encoder.encode("123456"));
        user.setBirthday(DateTime.of("1994-11-22", "yyyy-MM-dd")
                .getTime());
        user.setEmail((isAdmin ? "admin" : "user") + "@xkcoding.com");
        user.setPhone(isAdmin ? "17300000000" : "17300001111");
        user.setSex(1);
        user.setStatus(1);
        user.setCreateTime(DateUtil.current(false));
        user.setUpdateTime(DateUtil.current(false));
        userDao.save(user);
        return user;
    }

}
