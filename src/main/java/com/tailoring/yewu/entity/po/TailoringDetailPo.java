package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 裁剪明细表
 */
@Data
@Entity
@Table(name = "tailoring_detail")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringDetailPo extends AbstractAuditModel {


    /**
     * 任务ID: task_id
     */
    @Column(name = "task_id")
    private Long taskId;

    /**
     * 拉布ID: spreading_id
     */
    @Column(name = "spreading_id")
    private Long spreadingId;

    /**
     * 裁剪计划ID: tailoring_plan_id
     */
    @Column(name = "tailoring_plan_id")
    private Long tailoringPlanId;

    /**
     * 工单号: work_order_no
     */
    @Column(name = "work_order_no")
    private String workOrderNo;

    /**
     * 版型分组: type_group
     */
    @Column(name = "type_group")
    private String typeGroup;

    /**
     * 产品行号: product_line_no
     */
    @Column(name = "product_line_no")
    private Integer productLineNo;

    /**
     * 产品编码: product_code
     */
    @Column(name = "product_code")
    private String productCode;

    @ApiModelProperty(value="主/辅/补",example="主",required = true)
    @Column(name = "main_supplement")
    private String mainSupplement;
    /**
     * 成品数量: product_quantity
     */
    @Column(name = "product_quantity")
    private Integer productQuantity;

    /**
     * 布料编码: fabric_code
     */
    @Column(name = "fabric_code")
    private String fabricCode;


    /**
     * 拉布次数: spreading_count
     */
    @Column(name = "spreading_count")
    private Integer spreadingCount;

    /**
     * 层数（层高）: floor
     */
    private Integer floor;

    /**
     * 本次裁剪 ， 件数: quantity
     */
    private Integer quantity;

    /**
     * 本次换片: change_pieces_quantity
     */
    @Column(name = "change_pieces_quantity")
    private Integer changePiecesQuantity;

    /**
     * 排版件数: type_quantity
     */
    @Column(name = "type_quantity")
    private Integer typeQuantity;

    /**
     * 拉布件数: spreading_quantity
     */
    @Column(name = "spreading_quantity")
    private Integer spreadingQuantity;

    /**
     * 扎单数量: binding_quantity
     */
    @Column(name = "binding_quantity")
    private Integer bindingQuantity;

    /**
     * 剩余数量: left_quantity
     */
    @Column(name = "left_quantity")
    private Integer leftQuantity;

    /**
     * 详情状态: status
     */
    @Column(name = "status",columnDefinition = "0")
    private String status;
}

