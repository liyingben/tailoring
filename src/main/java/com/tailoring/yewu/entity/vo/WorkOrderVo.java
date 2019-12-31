package com.tailoring.yewu.entity.vo;

import com.tailoring.yewu.entity.po.WorkOrderPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WorkOrderVo extends WorkOrderPo implements Serializable {
    /**
     * 板型编号: type_number
     */
    private String typeNumber;
    /**
     * 布料编号: fabric_code
     */
    private String fabricCode;
    /**
     * 布料幅宽: fabric_width
     */
    private Double fabricWidth;
    /**
     * 换片率: change_rate
     */
    private Double changeRate;
    /**
     * 布料颜色: fabric_colour
     */
    private String fabricColour;

    @ApiModelProperty(value = "计划数量", example = "100", required = true)
    private Double quantity;
    @ApiModelProperty(value = "完成数量", example = "100", required = true)
    private Double workQuantity;






    public WorkOrderVo() {
    }

    public WorkOrderVo(Long id, String workOrderNo, String productLineNo, String productCode, String description, Double workOrderQuantity, Double remainingQuantity, Double boxQuantity, Double bindingQuantity, String dept, Date dueDate, String typeNumber, String fabricCode, Double fabricWidth, Double changeRate, String fabricColour, Double quantity, Double workQuantity) {
        super(id, workOrderNo, productLineNo, productCode, description, workOrderQuantity, remainingQuantity, boxQuantity, bindingQuantity, dept, dueDate);
        this.typeNumber = typeNumber;
        this.fabricCode = fabricCode;
        this.fabricWidth = fabricWidth;
        this.changeRate = changeRate;
        this.fabricColour = fabricColour;
        this.quantity = quantity;
        this.workQuantity = workQuantity;
    }
}