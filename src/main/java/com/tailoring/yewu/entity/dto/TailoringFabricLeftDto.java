package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value="布头表类",description="请求参数类" )
public class TailoringFabricLeftDto extends TailoringFabricInsertDto{

    /**
     * 拉布ID: spreading_id
     */
    @ApiModelProperty(value="拉布ID",example="1",required = true)
//    @Min(value = 0,message = "拉布ID不能空")
    private Long spreadingId;

    @ApiModelProperty(value="布头类型: null，0 正常，1 用完，2 布头，3 报废",example="1",required = true)
    @NotEmpty(message = "布头类型不能空")
    private Integer type;
}
