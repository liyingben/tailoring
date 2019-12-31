package com.tailoring.user.model;

import com.tailoring.user.model.unionkey.RolePermissionKey;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>
 * 角色-权限
 * </p>
 *
 * @package: com.tailoring.user.model
 * @description: 角色-权限
 * @author: ben
 * @date: Created in 2018-12-10 13:46
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Data
@Entity
@Table(name = "sec_role_permission")
public class RolePermission {
    /**
     * 主键
     */
    @EmbeddedId
    private RolePermissionKey id;
}
