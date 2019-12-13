package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="裁剪类",description="请求参数类" )
public class TailoringInsertDto {


    @ApiModelProperty(value="裁剪ID",example="1",required = true)
    private Integer tailoringId;

    @ApiModelProperty(value="拉布次数",example="10",required = true)
    private Integer spreadingCount;

    @ApiModelProperty(value="层数（层高）",example="50",required = true)
    private Integer floor;


    @ApiModelProperty(value="件数",example="15",required = true)
    private Integer quantity;

    @ApiModelProperty(value="裁剪计划",required = true)
    private List<TailoringPlanDto> tailoringPlans;

    @ApiModelProperty(value="裁剪布料",required = true)
    private List<TailoringFabricInsertDto> fabrics;


}
