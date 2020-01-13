package com.tailoring.user.repository;

import com.tailoring.user.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色 DAO
 * </p>
 *
 * @package: com.tailoring.user.repository
 * @description: 角色 DAO
 * @author: ben
 * @date: Created in 2018-12-07 16:20
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Repository
public interface GroupDao extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    /**
     * 根据用户id 查询角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    @Query(value = "SELECT sec_group.* FROM sec_group,sec_user,sec_user_group WHERE sec_user.id = sec_user_group.user_id AND sec_group.id = sec_user_group.group_id AND sec_user.id = :userId", nativeQuery = true)
    List<Group> selectByUserId(@Param("userId") Long userId);
}
