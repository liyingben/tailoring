package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "base_tailoring_distribution")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class BaseTailoringDistributionPo extends AbstractAuditModel {

    /**
     * 组别: group
     */
    @Column(name="`group`")
    private String group;

    /**
     * 裁剪成员1: member1
     */
    private String member1;

    /**
     * 裁剪成员2: member2
     */
    private String member2;

    /**
     * 裁剪成员3: member3
     */
    private String member3;

    /**
     * 分配比例1: percent1
     */
    private Integer percent1;

    /**
     * 分配比例2: percent2
     */
    private Integer percent2;

    /**
     * 分配比例3: percent3
     */
    private Integer percent3;

    /**
     * 分配比例合计: percent
     */
    @Column(name="`percent`")
    private Integer percent;

}

