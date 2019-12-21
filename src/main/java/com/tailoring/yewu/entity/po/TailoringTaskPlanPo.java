package com.tailoring.yewu.entity.po;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    @ApiModelProperty(value="工单号",example="4A-9253",required = true)
    @Column(name = "work_order_no")
    private String workOrderNo;

    @ApiModelProperty(value="产品编码",example="10000",required = true)
    @Column(name = "product_code")
    private String productCode;

    @ApiModelProperty(value="布料编码",example="FNA15RDB05",required = true)
    @Column(name = "fabric_code")
    private String fabricCode;

    @ApiModelProperty(value="版号",example="27号（共8衣片）",required = true)
    @Column(name = "type_number")
    private String typeNumber;
    /**
     * 版型分组
     */
    @Column(name = "type_group")
    private String typeGroup;

    /**
     * 本次裁剪件数
     */
    private Integer quantity;

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

    @ApiModelProperty(value="最大层高",example="4",required = true)
    @Column(name = "max_floor_height")
    private Integer maxFloorHeight;

}
