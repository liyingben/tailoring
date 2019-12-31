package com.tailoring.yewu.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BaseDictDto {


    @NotEmpty(message = "key不能为空")
    private String key;

    @NotEmpty(message = "值不能为空")
    private String value;

}

