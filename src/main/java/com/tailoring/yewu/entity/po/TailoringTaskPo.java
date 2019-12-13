package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 裁剪表
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tailoring_examine")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringTaskPo extends AbstractAuditModel {

    /**
     * 组别: group
     */
    @ApiModelProperty(value="组别",example="A3",required = true)
    @Column(name="`group`")
    private String group;

    /**
     * 组员: member
     */
    @ApiModelProperty(value="组员",example="郑德敏,陈善云,",required = true)
    private String member;

    /**
     * 布料编码: fabric_code
     */
    @ApiModelProperty(value="布料编码",example="FNA15RDB05",required = true)
    @Column(name = "fabric_code")
    private String fabricCode;

    /**
     * 版型分组: type_group
     */
    @Column(name = "type_group")
    private String typeGroup;

    /**
     * 裁剪编号: tailoring_no
     */
    @Column(name = "tailoring_no")
    private String tailoringNo;

    /**
     * 审核人: check_name
     */
    @Column(name = "check_name")
    private String checkName;

    /**
     * 审核时间: check_date
     */
    @Column(name = "check_date")
    private java.util.Date checkDate;

    /**
     * 回传ERP标记: ERPFLAG
     */
    private String erpflag;

    /**
     * 回传ERP ID: ERPID
     */
    private String erpid;

    /**
     * 回传ERP时间: ERPDATUM
     */
    private java.util.Date erpdatum;

    /**
     * 计划状态: status
     */
    @Column(name = "status",columnDefinition = "0")
    private String status;

}

