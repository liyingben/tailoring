package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TailoringRecoveryRecordDto {

    @ApiModelProperty(value = "任务id", example = "4", required = true)
    private Long taskId;

    @ApiModelProperty(value = "冲销数量", example = "4", required = true)
    private Double recoveryQuantity;

    @ApiModelProperty(value = "换片数量", example = "4", required = true)
    private Integer recoveryChangePiecesQuantity;

    @ApiModelProperty(value = "冲销人", example = "4", required = true)
    private String recoveryName;

}

