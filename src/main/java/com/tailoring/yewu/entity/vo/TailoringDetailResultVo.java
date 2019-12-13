package com.tailoring.yewu.entity.vo;


import com.tailoring.yewu.entity.dto.TailoringFabricInsertDto;
import com.tailoring.yewu.entity.dto.TailoringPlanDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel(value="裁剪扫码提交返回结果",description="请求参数类" )
public class TailoringDetailResultVo {
    /**
     * 结果状态码
     */
    private Integer fabricCode;

    /**
     * 件数
     */
    @ApiModelProperty(value="件数",example="1",required = true)
    private Integer quantity;

    @ApiModelProperty(value="裁剪计划",required = true)
    private List<TailoringPlanDto> tailoringPlans;

    @ApiModelProperty(value="裁剪布料",required = true)
    private List<TailoringFabricInsertDto> fabrics;

}
