package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(name = "tailoring_order", uniqueConstraints = {@UniqueConstraint(columnNames={"work_order_no","product_code","product_line_no","main_supplement"})})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringOrderPo extends AbstractAuditModel {


    @ApiModelProperty(value = "工单号", example = "4A-9253", required = true)
    @Column(name = "work_order_no",length = 30)
    private String workOrderNo;

    @ApiModelProperty(value = "产品编码", example = "10000", required = true)
    @Column(name = "product_code",length = 50)
    private String productCode;

    @ApiModelProperty(value = "产品行号", example = "30000", required = true)
    @Column(name = "product_line_no",length = 11)
    private Integer productLineNo;

    @ApiModelProperty(value = "主/辅/补", example = "主", required = true)
    @Column(name = "main_supplement",length = 2)
    private String mainSupplement;

    @ApiModelProperty(value = "工单数量", example = "1", required = true)
    @Column(name = "work_order_quantity")
    private Double workOrderQuantity;

    @ApiModelProperty(value = "计划数量", example = "100", required = true)
    @Column(name = "quantity")
    private Double quantity;

    @ApiModelProperty(value = "完成数量", example = "100", required = true)
    @Column(name = "work_quantity")
    private Double workQuantity;

    @ApiModelProperty(value = "布料编码", example = "FNA15RDB05", required = true)
    @Column(name = "fabric_code",length = 10)
    private String fabricCode;

    @ApiModelProperty(value = "布料幅宽", example = "1.55", required = true)
    @Column(name = "fabric_width")
    private Double fabricWidth;

    @ApiModelProperty(value = "布料颜色", example = "WH", required = true)
    @Column(name = "fabric_colour",length = 4)
    private String fabricColour;

    @ApiModelProperty(value = "装箱数量", example = "40", required = true)
    @Column(name = "box_quantity")
    private Integer boxQuantity;

    @ApiModelProperty(value = "扎单数量", example = "12", required = true)
    @Column(name = "binding_quantity")
    private Integer bindingQuantity;

    @ApiModelProperty(value = "换片数量", example = "4", required = true)
    @Column(name = "change_pieces_quantity")
    private Integer changePiecesQuantity;

    @ApiModelProperty(value = "排版件数", example = "4", required = true)
    @Column(name = "type_quantity")
    private Integer typeQuantity;

    @ApiModelProperty(value = "版型编号", example = "4", required = true)
    @Column(name = "type_number",length = 40)
    private String typeNumber;


    @ApiModelProperty(value = "订单状态", example = "1", required = true)
    @Column(name = "status",length = 6)
    private String status;

    @ApiModelProperty(value = "出货日期", example = "2019-12-11", required = true)
    @Column(name = "due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private java.util.Date dueDate;
}