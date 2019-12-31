package com.tailoring.user.repository;

import com.tailoring.user.model.RolePermission;
import com.tailoring.user.model.unionkey.RolePermissionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 角色-权限 DAO
 * </p>
 *
 * @package: com.tailoring.user.repository
 * @description: 角色-权限 DAO
 * @author: ben
 * @date: Created in 2018-12-10 13:45
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Repository
public interface RolePermissionDao extends JpaRepository<RolePermission, RolePermissionKey>, JpaSpecificationExecutor<RolePermission> {
}
