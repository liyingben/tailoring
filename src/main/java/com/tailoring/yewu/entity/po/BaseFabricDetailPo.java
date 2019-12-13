package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "base_fabric_detail")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class BaseFabricDetailPo extends AbstractAuditModel{

    /**
     * 布料编号: fabric_code
     */
    @Column(name = "fabric_code")
    private String fabricCode;

    /**
     * 布料名称: fabric_name
     */
    @Column(name = "fabric_name")
    private String fabricName;

    /**
     * 布料颜色: fabric_colour
     */
    @Column(name = "fabric_colour")
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

}

