package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "base_fabric_usage")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class BaseFabricUsagePo extends AbstractAuditModel {

    /**
     * 产品编码: product_code
     */
    @Column(name = "product_code")
    private String productCode;

    /**
     * 板型编号: type_number
     */
    @Column(name = "type_number")
    private String typeNumber;

    /**
     * 每扎数量: binding_quantity
     */
    @Column(name = "binding_quantity")
    private Integer bindingQuantity;

    /**
     * 挡风长度: flap_length
     */
    @Column(name = "flap_length")
    private String flapLength;

    /**
     * 挡风双面胶: ton_flap
     */
    @Column(name = "ton_flap")
    private String tonFlap;

    /**
     * 布料编号: fabric_code
     */
    @Column(name = "fabric_code")
    private String fabricCode;

    /**
     * 布料幅宽: fabric_width
     */
    @Column(name = "fabric_width")
    private Double fabricWidth;

    /**
     * 布料颜色: fabric_colour
     */
    @Column(name = "fabric_colour")
    private String fabricColour;

    /**
     * 每箱数量: box_quantity
     */
    @Column(name = "box_quantity")
    private Integer boxQuantity;

    /**
     * 排版件数: pieces_number
     */
    @Column(name = "pieces_number")
    private Integer piecesNumber;

    /**
     * 马克长度（米）: mark_width
     */
    @Column(name = "mark_width")
    private String markWidth;

    /**
     * 最大拉布层高: max_floor_height
     */
    @Column(name = "max_floor_height")
    private Integer maxFloorHeight;

    /**
     * 换片率: change_rate
     */
    @Column(name = "change_rate")
    private String changeRate;

    /**
     * 单件主面料用量 (米): fabric1_usage
     */
    @Column(name = "fabric1_usage")
    private String fabric1Usage;

    /**
     * WH/NV15后背用，单件用量(米): fabric2_usage
     */
    @Column(name = "fabric2_usage")
    private String fabric2Usage;

    /**
     * BLSS幅宽1.53米鞋底用，单件用量 (米): fabric3_usage
     */
    @Column(name = "fabric3_usage")
    private String fabric3Usage;

    /**
     * PVC鞋底幅宽1.372米，单件用量 (米): fabric4_usage
     */
    @Column(name = "fabric4_usage")
    private String fabric4Usage;

    /**
     * 透明面罩幅宽1.2米，单件用量 (米): fabric5_usage
     */
    @Column(name = "fabric5_usage")
    private String fabric5Usage;

    /**
     * WH25幅宽1.53米气管连接用，单件用量(米): fabric6_usage
     */
    @Column(name = "fabric6_usage")
    private String fabric6Usage;

    /**
     * BL15围脖用，单件用量(米): fabric7_usage
     */
    @Column(name = "fabric7_usage")
    private String fabric7Usage;

    /**
     * 其他单件用量(米): fabric8_usage
     */
    @Column(name = "fabric8_usage")
    private String fabric8Usage;

}

