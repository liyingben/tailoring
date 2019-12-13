package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageDto {

    @ApiModelProperty(required = false, value = "第几页(从1开始)", example = "1")
    private int pageNum;
    @ApiModelProperty(required = false, value = "每页条数", example = "20")
    private int pageSize = 20;
    private String orderBy;

}