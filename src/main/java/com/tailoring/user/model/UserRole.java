package com.tailoring.user.model;

import com.tailoring.user.model.unionkey.UserRoleKey;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * <p>
 * 用户角色关联
 * </p>
 *
 * @package: com.tailoring.user.model
 * @description: 用户角色关联
 * @author: ben
 * @date: Created in 2018-12-10 11:18
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Data
@Entity
@Table(name = "sec_user_role",uniqueConstraints = {@UniqueConstraint(columnNames={"user_id","role_id"})})
public class UserRole {
    /**
     * 主键
     */
    @EmbeddedId
    private UserRoleKey id;
}
