package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "tailoring_order")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringOrderPo extends AbstractAuditModel {

    /**
     * 工单号: work_order_no
     */
    @ApiModelProperty(value="工单号",example="4A-9253",required = true)
    @Column(name = "work_order_no")
    private String workOrderNo;

    /**
     * 产品行号: product_line_no
     */
    @ApiModelProperty(value="产品行号",example="30000",required = true)
    @Column(name = "product_line_no")
    private Integer productLineNo;

    /**
     * 产品编码: product_code
     */
    @ApiModelProperty(value="产品编码",example="10000",required = true)
    @Column(name = "product_code")
    private String productCode;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
    /**
     *
     */
    @Column(name = "remaining_quantity")
    private Double remainingQuantity;

    /**
     * 工单数量
     */
    @ApiModelProperty(value="工单数量",example="100",required = true)
    @Column(name = "quantity")
    private Double quantity;



    @ApiModelProperty(value="完成数量",example="100",required = true)
    @Column(name = "work_quantity")
    private Integer workQuantity;


    /**
     * 布料编码: fabric_code
     */
    @ApiModelProperty(value="布料编码",example="FNA15RDB05",required = true)
    @Column(name = "fabric_code")
    private String fabricCode;

    /**
     * 布料幅宽: fabric_width
     */
    @ApiModelProperty(value="布料幅宽",example="1.55",required = true)
    @Column(name = "fabric_width")
    private Double fabricWidth;

    /**
     * 布料颜色: fabric_colour
     */
    @ApiModelProperty(value="布料颜色",example="WH",required = true)
    @Column(name = "fabric_colour")
    private String fabricColour;

    /**
     * 装箱数量: box_quantity
     */
    @ApiModelProperty(value="装箱数量",example="40",required = true)
    @Column(name = "box_quantity")
    private Integer boxQuantity;

    /**
     * 扎单数量: binding_quantity
     */
    @ApiModelProperty(value="扎单数量",example="12",required = true)
    @Column(name = "binding_quantity")
    private Integer bindingQuantity;

    /**
     * 换片数量: change_pieces_quantity
     */
    @ApiModelProperty(value="换片数量",example="4",required = true)
    @Column(name = "change_pieces_quantity")
    private Integer changePiecesQuantity;



    /**
     * 排版件数: type_quantity
     */
    @ApiModelProperty(value="排版件数",example="4",required = true)
    @Column(name = "type_quantity")
    private Integer typeQuantity;



    /**
     * 出货日期: due_date
     */
    @ApiModelProperty(value="出货日期",example="2019-12-11",required = true)
    @Column(name = "due_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private java.util.Date dueDate;

    /**
     * 计划状态: status
     */
    @ApiModelProperty(value="计划状态",example="1",required = true)
    private String status;
}