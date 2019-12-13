package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "tailoring_fabric_consume")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringFabricConsumePo extends AbstractAuditModel {


    @Column(name = "work_order_no")
    private String workOrderNo;
    @Column(name = "product_code")
    private String productCode;
    @Column(name = "product_line_no")
    private Integer productLineNo;
    @Column(name = "consume_quantity")
    private Double consumeQuantity;

    private String erpflag;

    private String erpid;

    private Date erpdatum;


}