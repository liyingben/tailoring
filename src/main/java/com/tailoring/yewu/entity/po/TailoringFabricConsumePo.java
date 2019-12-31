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


    @Column(name = "work_order_no" ,length = 30)
    private String workOrderNo;
    @Column(name = "product_code",length = 50)
    private String productCode;
    @Column(name = "product_line_no",length = 11)
    private Integer productLineNo;
    @Column(name = "consume_quantity")
    private Double consumeQuantity;
    @Column(name = "erpflag",length = 1)
    private String erpflag;
    @Column(name = "erpid",length = 1)
    private String erpid;

    private Date erpdatum;


}