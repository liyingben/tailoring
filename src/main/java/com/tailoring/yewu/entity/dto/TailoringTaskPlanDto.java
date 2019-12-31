package com.tailoring.yewu.entity.dto;


import com.tailoring.yewu.entity.po.TailoringPlanPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "裁剪计划类", description = "请求参数类")
public class TailoringTaskPlanDto extends TailoringPlanPo {

    /**
     * 版型分组
     */
    @ApiModelProperty(value = "版型分组", example = "1", required = true)
    @NotEmpty(message = "版型分组不能空")
    private String typeGroup;

    /**
     * 本次裁剪件数
     */
    @ApiModelProperty(value = "本次裁剪件数", example = "1", required = true)
    @Min(0)
    private Integer quantity;

    /**
     * 本次换片数量:
     */
    @ApiModelProperty(value = "换片数量", example = "4", required = true)
    @Min(0)
    private Integer changePiecesQuantity;
    /**
     * 层高
     */
    @ApiModelProperty(value = "层高", example = "1", required = true)
    @Min(1)
    private Integer floor;

    /**
     * 排版件数:
     */
    @ApiModelProperty(value = "排版件数", example = "4", required = true)
    @Min(1)
    private Integer typeQuantity;
}
