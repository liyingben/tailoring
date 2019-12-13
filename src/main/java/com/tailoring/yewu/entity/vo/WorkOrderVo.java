package com.tailoring.yewu.entity.vo;

import com.tailoring.yewu.entity.po.WorkOrderPo;
import lombok.Data;

import java.io.Serializable;

@Data
public class WorkOrderVo extends WorkOrderPo implements Serializable {

    private String typeNumber;
    private String fabricCode;
    private Double fabricWidth;
    private String changeRate;
    private String fabricColour;

}