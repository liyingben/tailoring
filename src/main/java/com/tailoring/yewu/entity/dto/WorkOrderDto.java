package com.tailoring.yewu.entity.dto;


import com.tailoring.yewu.entity.po.WorkOrderPo;
import lombok.Data;

import java.io.Serializable;

@Data
public class WorkOrderDto extends WorkOrderPo implements Serializable {

    private String typeNumber;
    private String fabricCode;
    private Double fabricWidth;
    private String changeRate;
    private String fabricColour;

}