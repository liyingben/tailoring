package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="布料记录类",description="请求参数类" )
public class TailoringFabricRecordDto implements Serializable{

    private static final long serialVersionUID=1L;

    // alias
    public static final String TABLE_ALIAS="TailoringFabricRecord";

    /**
     * 裁剪布料消耗ID: id
     */
    @ApiModelProperty(value="裁剪布料消耗ID",allowEmptyValue=true)
    private Integer id;

    /**
     * 裁剪ID: tailoring_id
     */
    @ApiModelProperty(value="裁剪ID",example="1",required = true)
    private Integer tailoringId;

    /**
     * 布料编码: fabric_code
     */
    @ApiModelProperty(value="布料编码",example="123",required = true)
    private String fabricCode;

    /**
     * 布料卷号: reel_number
     */
    @ApiModelProperty(value="布料卷号",example="123",required = true)
    private String reelNumber;

    /**
     * 布批号: lot_number
     */
    @ApiModelProperty(value="布批号",example="12",required = true)
    private String lotNumber;

    /**
     * 理论长度（米）: theory_length
     */
    @ApiModelProperty(value="理论长度（米）",example="14.5",required = true)
    private Double theoryLength;

}

