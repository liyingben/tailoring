package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 裁剪计划表
 */
@Data
@Entity
@Table(name = "tailoring_plan")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringPlanPo extends AbstractAuditModel {


    /**
     * 工单号: work_order_no
     */
    @ApiModelProperty(value="工单号",example="4A-9253",required = true)
    @Column(name = "work_order_no")
    private String workOrderNo;



    /**
     * 版号: type_number
     */
    @ApiModelProperty(value="版号",example="27号（共8衣片）",required = true)
    @Column(name = "type_number")
    private String typeNumber;

    /**
     * 组别: group
     */
    @ApiModelProperty(value="组别",example="A3",required = true)
    @Column(name="`group`")
    private String group;

    /**
     * 组员: member
     */
    @ApiModelProperty(value="组员",example="郑德敏,陈善云,",required = true)
    private String member;

    /**
     * 主/辅/补: main_supplement
     */
    @ApiModelProperty(value="主/辅/补",example="主",required = true)
    @Column(name = "main_supplement")
    private String mainSupplement;

    /**
     * 计划数量: quantity
     */
    @ApiModelProperty(value="计划数量",example="100",required = true)
    private Integer quantity;

    /**
     * 出货日期: due_date
     */
    @ApiModelProperty(value="出货日期",example="2019-12-11",required = true)
    @Column(name = "due_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private java.util.Date dueDate;

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
     * 计划状态: status
     */
    @ApiModelProperty(value="计划状态",example="1",required = true)
    private String status;


    /**
     * 计划数量: quantity
     */
    @ApiModelProperty(value="最大可裁剪数量",example="100",required = true)
    @Column(name = "max_quantity")
    private Integer maxQuantity;


    @ApiModelProperty(value="最大可换片数量",example="4",required = true)
    @Column(name = "max_change_pieces_quantity")
    private Integer maxChangePiecesQuantity;

}

