package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="裁剪布料类",description="请求参数类" )
public class TailoringFabricInsertDto {

    /**
     * 布料编号: fabric_code
     */
    @ApiModelProperty(example="FNA15RDB05",required = true)
    private String fabricCode;

    /**
     * 布料卷号: reel_number
     */
    @ApiModelProperty(example="1212",required = true)
    private String reelNumber;

    /**
     * 布批号: lot_number
     */
    @ApiModelProperty(example="123",required = true)
    private String lotNumber;
    /**
     * 理论长度（米）: theory_length
     */
    @ApiModelProperty(example="150",required = true)
    private Double theoryLength;

    /**
     * 实际幅宽: actual_fabric_width
     */
    @ApiModelProperty(example="1.5",required = true)
    private Double actualFabricWidth;

    /**
     * 理论幅宽（米）: theory_fabric_width
     */
    @ApiModelProperty(example="1.4",required = true)
    private Double theoryFabricWidth;
}
