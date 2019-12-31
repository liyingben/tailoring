package com.tailoring.yewu.entity.vo;


import com.tailoring.yewu.entity.po.TailoringFabricLeftPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "布头长度", description = "请求布头长度")
public class TailoringFabricLeftTheoryLengthVo {


    @ApiModelProperty(value = "拉布长度", required = true)
    public Double theoryLength;

    /**
     * 同次保存的布头列表
     */
    @ApiModelProperty(value = "同次保存的布头列表", required = false)
    public List<TailoringFabricLeftPo> fagEndList;


}
