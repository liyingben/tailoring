package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 裁剪表
 */
@Data
@ApiModel(value="裁剪审核通过类",description="请求参数类" )
public class TailoringTaskPassDto implements Serializable{


    /**
     * 裁剪ID: id
     */
    @ApiModelProperty(value="裁剪ID",example="1")
    private Long id;


    /**
     * 审核人: check_name
     */
    @ApiModelProperty(value="审核人",example="1")
    private String checkName;



}

