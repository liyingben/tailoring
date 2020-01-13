package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@ApiModel(value = "添加布头类", description = "请求参数类")
public class TailoringFabricLeftsDto {


    @ApiModelProperty(value = "拉布次数", example = "10", required = true)
    @Min(value = 1)
    private Integer spreadingCount;

    @ApiModelProperty(value = "拉布长度", example = "15", required = true)
    @Min(value = 0)
    private Integer quantity;

    @Valid
    @ApiModelProperty(value = "裁剪布料", required = true)
    private List<TailoringFabricLeftDto> fabrics;


}
