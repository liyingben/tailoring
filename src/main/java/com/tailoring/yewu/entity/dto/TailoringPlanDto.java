package com.tailoring.yewu.entity.dto;


import com.tailoring.yewu.entity.po.TailoringPlanPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="裁剪计划类",description="请求参数类" )
public class TailoringPlanDto extends TailoringPlanPo {

    /**
     * 版型分组
     */
    @ApiModelProperty(value="版型分组",example="1",required = true)
    private String typeGroup;

    /**
     * 件数
     */
    @ApiModelProperty(value="件数",example="1",required = true)
    private Integer quantity;

    /**
     * 长度
     */
    @ApiModelProperty(value="长度",example="1",required = true)
    private Integer length;
    /**
     * 层高
     */
    @ApiModelProperty(value="层高",example="1",required = true)
    private Integer floor;
}
