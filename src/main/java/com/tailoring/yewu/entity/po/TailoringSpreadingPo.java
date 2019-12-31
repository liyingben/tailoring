package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 裁剪拉布表
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tailoring_spreading")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringSpreadingPo extends AbstractAuditModel {

    @ApiModelProperty(value = "taskID", example = "1", required = true)
    @Column(name = "task_id")
    private Long taskId;

    @ApiModelProperty(value = "拉布次数", example = "10", required = true)
    @Column(name = "spreading_count")
    private Integer spreadingCount;

    @ApiModelProperty(value = "层数（层高）", example = "50", required = true)
    private Integer floor;

    @ApiModelProperty(value = "拉布长度", example = "15", required = true)
    private Integer quantity;

    @ApiModelProperty(value = "卷数", example = "15", required = true)
    @Column(name = "reel_count")
    private Integer reelCount;

    @ApiModelProperty(value = "计划数", example = "15", required = true)
    @Column(name = "plan_count")
    private Integer planCount;

}

