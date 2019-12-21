package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(value="裁剪拉布类",description="请求参数类" )
public class TailoringSpreadingDto {


    @ApiModelProperty(value="taskID",example="1",required = true)
    @Min(0)
    private Long taskId;

    @ApiModelProperty(value="拉布次数",example="10",required = true)
    @Min(value = 1)
    private Integer spreadingCount;

    @ApiModelProperty(value="层数（层高）",example="50",required = true)
    @Min(value = 1)
    private Integer floor;

    @ApiModelProperty(value="拉布长度",example="15",required = true)
    @Min(value = 0)
    private Integer quantity;

    @ApiModelProperty(value="裁剪计划",required = true)
    private List<TailoringPlanDto> tailoringPlans;

    @Valid
    @ApiModelProperty(value="裁剪布料",required = true)
    private List<TailoringFabricInsertDto> fabrics;


}
