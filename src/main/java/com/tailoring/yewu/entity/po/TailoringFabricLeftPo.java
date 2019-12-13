package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
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
     * 类型（1：布头，2：废弃）: type
     */
    private String type;

    /**
     * 布料编码: fabric_code
     */
    @Column(name = "fabric_code")
    private String fabricCode;

    /**
     * 布料卷号: reel_number
     */
    @Column(name = "reel_number")
    private String reelNumber;

    /**
     * 布批号: lot_number
     */
    @Column(name = "lot_number")
    private String lotNumber;

    /**
     * 理论长度: theory_length
     */
    @Column(name = "theory_length")
    private Double theoryLength;

    /**
     * 组别: group
     */
    @Column(name="`group`")
    private String group;

    /**
     * 日期: date
     */
    private java.util.Date date;

}

