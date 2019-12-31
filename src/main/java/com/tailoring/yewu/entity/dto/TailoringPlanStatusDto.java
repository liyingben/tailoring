package com.tailoring.yewu.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "修改裁剪状态", description = "请求参数类")
public class TailoringPlanStatusDto {

    @ApiModelProperty(value = "id", example = "1", required = true)
    private Long id;


    @ApiModelProperty(value = "计划状态", example = "1", required = true)
    private String status;
}
