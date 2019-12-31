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
 * 任务计划汇总
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tailoring_task_plan")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringTaskPlanPo extends AbstractAuditModel {

    @Column(name = "task_id")
    private Long taskId;

    /**
     * 裁剪计划ID: tailoring_plan_id
     */
    @Column(name = "tailoring_plan_id")
    private Long tailoringPlanId;

    @ApiModelProperty(value = "工单号", example = "4A-9253", required = true)
    @Column(name = "work_order_no",length = 30)
    private String workOrderNo;

    @ApiModelProperty(value = "产品编码", example = "10000", required = true)
    @Column(name = "product_code",length = 50)
    private String productCode;

    @ApiModelProperty(value = "产品行号", example = "10000", required = true)
    @Column(name = "product_line_no",length = 11)
    private Integer productLineNo;


    @ApiModelProperty(value = "主/辅/补", example = "主", required = true)
    @Column(name = "main_supplement",length = 2)
    private String mainSupplement;

    @ApiModelProperty(value = "布料编码", example = "FNA15RDB05", required = true)
    @Column(name = "fabric_code",length = 10)
    private String fabricCode;

    @ApiModelProperty(value = "版号", example = "27号（共8衣片）", required = true)
    @Column(name = "type_number",length = 40)
    private String typeNumber;
    /**
     * 版型分组
     */
    @Column(name = "type_group",length = 2)
    private String typeGroup;

    /**
     * 本次裁剪件数
     */
    private Integer quantity;

    @ApiModelProperty(value = "最大层高", example = "4")
    @Column(name = "max_quantity")
    private Integer maxQuantity;

    /**
     * 本次换片数量:
     */
    @Column(name = "change_pieces_quantity")
    private Integer changePiecesQuantity;
    /**
     * 层高
     */
    private Integer floor;

    /**
     * 排版件数:
     */
    @Column(name = "type_quantity")
    private Integer typeQuantity;

    @ApiModelProperty(value = "最大层高", example = "4", required = true)
    @Column(name = "max_floor_height")
    private Integer maxFloorHeight;



    /**
     * 成品数量: product_quantity
     */
    @Column(name = "product_quantity")
    private Integer productQuantity;

    /**
     * 拉布次数: spreading_count
     */
    @Column(name = "spreading_count")
    private Integer spreadingCount;

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
     * 差异系数(米): fabric_width_foot
     */
    @Column(name = "fabric_length_difference")
    private Integer fabricLengthDifference;

}
