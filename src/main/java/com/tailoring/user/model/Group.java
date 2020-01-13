package com.tailoring.user.model;

import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>
 * 角色
 * </p>
 *
 * @package: com.tailoring.user.model
 * @description: 角色
 * @author: ben
 * @date: Created in 2018-12-07 15:45
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Data
@Entity
@Table(name = "sec_group")
public class Group extends AbstractAuditModel {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 组名
     */
    @Column(name = "team",length = 20)
    private String team;

    /**
     * 描述
     */
    @Column(name = "team_man",length = 100)
    private String teamMan;


    /**
     * 任务状态: status
     */
    @Column(name = "status",length = 6)
    private String status;

}
