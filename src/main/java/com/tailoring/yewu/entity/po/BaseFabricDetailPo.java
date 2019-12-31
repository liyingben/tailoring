package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 在《布料明细》增加”差异系数“字段。根据布料编码取。
 */
@Data
@Entity
@Table(name = "base_fabric_detail")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class BaseFabricDetailPo extends AbstractAuditModel {

    /**
     * 布料编号: fabric_code
     */
    @Column(name = "fabric_code",length = 10)
    private String fabricCode;

    /**
     * 布料名称: fabric_name
     */
    @Column(name = "fabric_name",length = 50)
    private String fabricName;

    /**
     * 布料颜色: fabric_colour
     */
    @Column(name = "fabric_colour",length = 4)
    private String fabricColour;

    /**
     * 布料克重: fabric_weight
     */
    @Column(name = "fabric_weight")
    private Integer fabricWeight;

    /**
     * 布料幅宽(米): fabric_width_meter
     */
    @Column(name = "fabric_width_meter")
    private Double fabricWidthMeter;

    /**
     * 布料幅宽(英尺): fabric_width_foot
     */
    @Column(name = "fabric_width_foot")
    private Integer fabricWidthFoot;

    /**
     * 差异系数(米): fabric_width_foot
     */
    @Column(name = "fabric_length_difference")
    private Integer fabricLengthDifference;


    /**
     * 是否控制布匹: is_control_fabric
     * ”是否控制布匹“字段（ Bit  1,0），0 布匹号可以不一致，1 必须一致。
     */
    @Column(name = "is_control_fabric",length = 1)
    private String isControlFabric;

}

