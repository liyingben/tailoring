package com.tailoring.yewu.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 裁剪表
 */
@Data
@ApiModel(value="裁剪类",description="请求参数类" )
public class TailoringTaskDto implements Serializable{

    private static final long serialVersionUID=1L;

    // alias
    public static final String TABLE_ALIAS="Tailoring";

    /**
     * 裁剪ID: id
     */
    @ApiModelProperty(value="裁剪ID",example="1")
    private Integer id;

    /**
     * 裁剪编号: tailoring_no
     */
    @ApiModelProperty(value="裁剪编号",example="1")
    private String tailoringNo;

    /**
     * 审核人: check_name
     */
    @ApiModelProperty(value="审核人",example="1")
    private String checkName;

    /**
     * 审核时间: check_date
     */
    @ApiModelProperty(value="审核时间",example="1")
    private java.util.Date checkDate;

    /**
     * 回传ERP标记: ERPFLAG
     */
    @ApiModelProperty(value="回传ERP标记",example="1")
    private String erpflag;

    /**
     * 回传ERP ID: ERPID
     */
    @ApiModelProperty(value="回传ERP ID",example="1")
    private String erpid;

    /**
     * 回传ERP时间: ERPDATUM
     */
    @ApiModelProperty(value="回传ERP时间",example="1")
    private java.util.Date erpdatum;

}

