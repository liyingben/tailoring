package com.tailoring.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
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
     *  类别
     */
    @Column(name = "team_type",length = 10)
    private String teamType;


    /**
     * 任务状态: status 0 正常，
     */
    @Column(name = "status",length = 6)
    private String status;

}
