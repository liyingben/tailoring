package com.tailoring.user.repository;

import com.tailoring.user.model.UserGroup;
import com.tailoring.user.model.UserRole;
import com.tailoring.user.model.unionkey.UserGroupKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户角色 DAO
 * </p>
 *
 * @package: com.tailoring.user.repository
 * @description: 用户角色 DAO
 * @author: ben
 * @date: Created in 2018-12-10 11:24
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Repository
public interface UserGroupDao extends JpaRepository<UserGroup, UserGroupKey>, JpaSpecificationExecutor<UserRole> {

}
