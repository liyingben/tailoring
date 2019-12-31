package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tailoring_recovery_record")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringRecoveryRecordPo extends AbstractAuditModel {

    @Column(name = "task_id")
    private Long taskId;


    /**
     * 冲销数量: recovery_quantity
     */
    @Column(name = "recovery_quantity")
    private Double recoveryQuantity;

    @ApiModelProperty(value = "换片数量", example = "4", required = true)
    @Column(name = "recovery_change_pieces_quantity")
    private Integer recoveryChangePiecesQuantity;

    /**
     * 冲销人: recovery_name
     */
    @Column(name = "recovery_name",length = 10)
    private String recoveryName;

    /**
     * 冲销时间: recovery_time
     */
    @Column(name = "recovery_time")
    private java.util.Date recoveryTime;

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

}

