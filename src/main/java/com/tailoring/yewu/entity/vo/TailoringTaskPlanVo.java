package com.tailoring.yewu.entity.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import com.tailoring.yewu.entity.po.TailoringTaskPlanPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 任务计划汇总
 */
@Data
@ApiModel(value = "任务计划信息", description = "请求参数类")
public class TailoringTaskPlanVo extends TailoringTaskPlanPo {

    @ApiModelProperty(value = "完成数量", example = "100", required = true)
    private Integer workQuantity;

}
