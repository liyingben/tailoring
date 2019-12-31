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
 * 裁剪表
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tailoring_task")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringTaskPo extends AbstractAuditModel {

    /**
     * 裁剪编号: tailoring_no
     */
    @Column(name = "tailoring_no",length = 7)
    private String tailoringNo;

    /**
     * 组别: group
     */
    @ApiModelProperty(value = "组别", example = "A3", required = true)
    @Column(name = "`group`",length = 20)
    private String group;

    /**
     * 组员: member
     */
    @ApiModelProperty(value = "组员", example = "郑德敏,陈善云,", required = true)
    @Column(name = "member",length = 40)
    private String member;

    /**
     * 布料编码: fabric_code
     */
    @ApiModelProperty(value = "布料编码", example = "FNA15RDB05", required = true)
    @Column(name = "fabric_code",length = 10)
    private String fabricCode;

    /**
     * 版型分组: type_group
     */
    @Column(name = "type_group",length = 2)
    private String typeGroup;

    /**
     * 是否控制布匹: is_control_fabric
     * 1 ”是否控制布匹“字段（ Bit  1,0）。
     * 2、扫码页判断，如果BIT是0，则不控制”布匹号不一致“。
     */
    @Column(name = "is_control_fabric",length = 1)
    private String isControlFabric;


    /**
     * 任务状态: status
     */
    @Column(name = "status",length = 6)
    private String status;

}

