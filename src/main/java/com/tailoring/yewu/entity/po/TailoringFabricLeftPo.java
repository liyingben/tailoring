package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tailoring_fabric_left")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringFabricLeftPo extends AbstractAuditModel {


    /**
     * 拉布ID: spreading_id
     */

    @Column(name = "spreading_id")
    private Long spreadingId;

    /**
     * UUID: uuid
     */
    @ApiModelProperty(hidden = true)
    @Column(name = "uuid", length = 50)
    private String uuid;

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
    @ApiModelProperty(value = "布料编号", example = "FNA15RDB05", required = true)
    @Column(name = "fabric_code",length = 10)
    private String fabricCode;

    /**
     * 布料卷号: reel_number
     */
    @ApiModelProperty(value = "布料卷号", example = "1212", required = true)
    @Column(name = "reel_number",length = 25)
    private String reelNumber;

    /**
     * 布批号: lot_number
     */
    @ApiModelProperty(value = "布批号", example = "123", required = true)
    @Column(name = "lot_number",length = 6)
    private String lotNumber;

    /**
     * 理论长度: theory_length
     */
    @ApiModelProperty(value = "理论长度（米）", required = true)
    @Column(name = "theory_length")
    private Double theoryLength;

    /**
     * 实际长度: actual_length
     */
    @ApiModelProperty(value = "实际长度", required = true)
    @Column(name = "actual_length")
    private Double actualLength;


    /**
     * 实际幅宽: actual_fabric_width
     */
    @ApiModelProperty(value = "实际幅宽", required = true)
    @Column(name = "actual_fabric_width")
    private Double actualFabricWidth;

    /**
     * 理论幅宽（米）: theory_fabric_width
     */
    @ApiModelProperty(value = "理论幅宽（米）", required = true)
    @Column(name = "theory_fabric_width")
    private Double theoryFabricWidth;
    /**
     * 备注: remark
     */
    @Column(name = "remark",length = 50)
    private String remark;

    /**
     * 类型（1：布头，2：废弃）: type
     */
    @ApiModelProperty(value = "布料类型: null，0 正常，1：布头，2：废弃,3：用完）.note:预留", example = "1", required = true)
    @Column(name = "type",length = 1)
    private String type;


}

