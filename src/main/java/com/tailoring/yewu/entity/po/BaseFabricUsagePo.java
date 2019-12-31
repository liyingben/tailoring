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
    @Column(name = "product_code" ,length = 50)
    private String productCode;

    /**
     * 板型编号: type_number
     */
    @Column(name = "type_number",length = 40)
    private String typeNumber;

    /**
     * 每扎数量: binding_quantity
     */
    @Column(name = "binding_quantity")
    private Integer bindingQuantity;

    /**
     * 挡风长度: flap_length
     */
    @Column(name = "flap_length" ,length = 7)
    private String flapLength;

    /**
     * 挡风双面胶: ton_flap
     */
    @Column(name = "ton_flap" ,length = 10)
    private String tonFlap;

    /**
     * 布料编号: fabric_code
     */
    @Column(name = "fabric_code",length = 10)
    private String fabricCode;

    /**
     * 布料幅宽: fabric_width
     */
    @Column(name = "fabric_width")
    private Double fabricWidth;

    /**
     * 布料颜色: fabric_colour
     */
    @Column(name = "fabric_colour",length = 4)
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
    private Double markWidth;

    /**
     * 最大拉布层高: max_floor_height
     */
    @Column(name = "max_floor_height")
    private Integer maxFloorHeight;

    /**
     * 换片率: change_rate
     */
    @Column(name = "change_rate")
    private Double changeRate;

    /**
     * 单件主面料用量 (米): fabric1_usage
     */
    @Column(name = "fabric1_usage")
    private Double fabric1Usage;

    /**
     * WH/NV15后背用，单件用量(米): fabric2_usage
     */
    @Column(name = "fabric2_usage")
    private Double fabric2Usage;

    /**
     * BLSS幅宽1.53米鞋底用，单件用量 (米): fabric3_usage
     */
    @Column(name = "fabric3_usage")
    private Double fabric3Usage;

    /**
     * PVC鞋底幅宽1.372米，单件用量 (米): fabric4_usage
     */
    @Column(name = "fabric4_usage")
    private Double fabric4Usage;

    /**
     * 透明面罩幅宽1.2米，单件用量 (米): fabric5_usage
     */
    @Column(name = "fabric5_usage")
    private Double fabric5Usage;

    /**
     * WH25幅宽1.53米气管连接用，单件用量(米): fabric6_usage
     */
    @Column(name = "fabric6_usage")
    private Double fabric6Usage;

    /**
     * BL15围脖用，单件用量(米): fabric7_usage
     */
    @Column(name = "fabric7_usage")
    private Double fabric7Usage;

    /**
     * 其他单件用量(米): fabric8_usage
     */
    @Column(name = "fabric8_usage")
    private Double fabric8Usage;

}

