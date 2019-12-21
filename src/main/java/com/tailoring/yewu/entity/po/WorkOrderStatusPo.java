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
@Table(name = "v_erp_prod_order_status")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class WorkOrderStatusPo extends AbstractAuditModel {

    /**
     *工单号码
     */
    @Column(name = "work_order_no")
    private String workOrderNo;

    /**
     *产品编号
     */
    @Column(name = "product_code")
    private String productCode;

    /**
     * 工单数量
     */
    @Column(name = "work_order_quantity")
    private Double workOrderQuantity;


    /**
     * 计划数量: quantity
     */
    @ApiModelProperty(value="最大可裁剪数量",example="100",required = true)
    @Column(name = "max_quantity")
    private Integer maxQuantity;


    /**
     * 工单状态: status 0,正常，1有计划，2计划完成
     */
    @ApiModelProperty(value="工单状态",example="1",required = true)
    @Column(name = "status",length = 10)
    private String status;
}