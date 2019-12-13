package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tailoring_recovery_record")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringRecoveryRecordPo extends AbstractAuditModel {


    /**
     * 裁剪明细ID: tailoring_detail_id
     */
    @Column(name = "tailoring_detail_id")
    private Integer tailoringDetailId;

    /**
     * 冲销数量: recovery_quantity
     */
    @Column(name = "recovery_quantity")
    private Double recoveryQuantity;

    /**
     * 冲销人: recovery_name
     */
    @Column(name = "recovery_name")
    private String recoveryName;

    /**
     * 冲销时间: recovery_time
     */
    @Column(name = "recovery_time")
    private java.util.Date recoveryTime;

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

}

