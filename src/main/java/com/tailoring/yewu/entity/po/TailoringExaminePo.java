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
import javax.persistence.UniqueConstraint;

/**
 * 审核表
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tailoring_examine", uniqueConstraints = {@UniqueConstraint(columnNames="task_ids"),@UniqueConstraint(columnNames="tailoring_no")})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringExaminePo extends AbstractAuditModel {
    /**
     * 裁剪编号: tailoring_no
     * NO:每个布料使用记录生成一个流水号：年+S+流水号。例：19S2627.
     */
    @Column(name = "tailoring_no",length = 7)
    private String tailoringNo;

    /**
     * 组别: group
     */
    @ApiModelProperty(value = "组别", example = "A3", required = true)
    @Column(name = "`group`",length = 20)
    private String group;

    /**
     * 组员: member
     */
    @ApiModelProperty(value = "组员", example = "郑德敏,陈善云,", required = true)
    @Column(name = "member",length = 40)
    private String member;

    /**
     * 布料编码: fabric_code
     */
    @ApiModelProperty(value = "布料编码", example = "FNA15RDB05", required = true)
    @Column(name = "fabric_code",length = 10)
    private String fabricCode;


    @ApiModelProperty(value = "布料幅宽", example = "1.55", required = true)
    @Column(name = "fabric_width")
    private Double fabricWidth;

    @ApiModelProperty(value = "布料颜色", example = "WH", required = true)
    @Column(name = "fabric_colour" ,length = 4)
    private String fabricColour;
    /**
     * 布料克重: fabric_weight
     */
    @Column(name = "fabric_weight")
    private Integer fabricWeight;
    /**
     * 冲销数量: recovery_quantity
     */
    @Column(name = "recovery_quantity")
    private Double recoveryQuantity;

    /**
     * 审核人: check_name
     */
    @Column(name = "check_name",length = 10)
    private String checkName;

    /**
     * 审核时间: check_date
     */
    @Column(name = "check_date")
    private java.util.Date checkDate;

    /**
     * 回传ERP标记: ERPFLAG
     */
    @Column(name = "erpflag",length = 1)
    private String erpflag;

    /**
     * 回传ERP ID: ERPID
     */
    @Column(name = "erpid",length = 1)
    private String erpid;

    /**
     * 回传ERP时间: ERPDATUM
     */
    private java.util.Date erpdatum;

    /**
     * 任务状态: status
     */
    @Column(name = "status",length = 6)
    private String status;


    @Column(name = "task_ids")
    private String taskIds;


}

