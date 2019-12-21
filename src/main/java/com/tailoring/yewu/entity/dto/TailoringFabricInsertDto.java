package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value="裁剪布料类",description="请求参数类" )
public class TailoringFabricInsertDto {

    /**
     * 布料编号: fabric_code
     */
    @NotEmpty(message = "布料编号不能空")
    @ApiModelProperty(value="布料编号",example="FNA15RDB05",required = true)
    private String fabricCode;

    /**
     * 布料卷号: reel_number
     */
    @NotEmpty(message = "布料卷号不能空")
    @ApiModelProperty(value="布料卷号",example="1212",required = true)
    private String reelNumber;

    /**
     * 布批号: lot_number
     */
    @NotEmpty(message = "布批号不能空")
    @ApiModelProperty(value="布批号",example="123",required = true)
    private String lotNumber;
    /**
     * 理论长度（米）: theory_length
     */
    @ApiModelProperty(value="理论长度（米）",example="150",required = true)
    @Min(0)
    private Double theoryLength;

    /**
     * 实际幅宽: actual_fabric_width
     */
    @ApiModelProperty(value="实际幅宽",example="1.5",required = true)
    private Double actualFabricWidth;

    /**
     * 理论幅宽（米）: theory_fabric_width
     */
    @ApiModelProperty(value="理论幅宽（米）",example="1.4",required = true)
    private Double theoryFabricWidth;


}
