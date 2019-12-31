package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "base_hpw")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class BaseHpwPo extends AbstractAuditModel {


    /**
     * 产品组代码: product_group_code
     */
    @Column(name = "product_group_code",length = 10)
    private String productGroupCode;

    /**
     * 产品编码(按接缝和款式): product_code
     */
    @Column(name = "product_code",length = 50)
    private String productCode;

    /**
     * 工程编码: eng_id
     */
    @Column(name = "eng_id",length = 20)
    private String engId;

    /**
     * 物料目录代码: item_category_code
     */
    @Column(name = "item_category_code",length = 5)
    private String itemCategoryCode;

    /**
     * 集团物料编号: group_item_Code
     */
    @Column(name = "group_item_Code",length = 20)
    private String groupItemCode;

    /**
     * BOM 版本: bom_version
     */
    @Column(name = "bom_version",length = 4)
    private String bomVersion;

    /**
     * 基本单位: base_unit
     */
    @Column(name = "base_unit",length = 3)
    private String baseUnit;

    /**
     * 单位体积: unit_volume
     */
    @Column(name = "unit_volume")
    private Double unitVolume;

    /**
     * 尺寸: size
     */
    @Column(name = "size",length = 8)
    private String size;

    /**
     * 颜色: colour
     */
    @Column(name = "colour",length = 2)
    private String colour;

    /**
     * 每箱数量: box_quantity
     */
    @Column(name = "box_quantity")
    private Integer boxQuantity;

    /**
     * 中文描述: descripton_cn
     */
    @Column(name = "descripton_cn",length = 30)
    private String descriptonCn;

    /**
     * 英文描述: descripton_en
     */
    @Column(name = "descripton_en",length = 50)
    private String descriptonEn;

    /**
     * 关税编号: tariff_no
     */
    @Column(name = "tariff_no")
    private String tariffNo;

    /**
     * 关税说明: tariff_description
     */
    @Column(name = "tariff_description",length = 100)
    private String tariffDescription;

    /**
     * mxl(货仓代码): default_bin_code_mxl
     */
    @Column(name = "default_bin_code_mxl",length = 10)
    private String defaultBinCodeMxl;

    /**
     * msl(货仓代码): default_bin_code_msl
     */
    @Column(name = "default_bin_code_msl",length = 10)
    private String defaultBinCodeMsl;

    /**
     * 贸易方式MXL: process_type_mxl
     */
    @Column(name = "process_type_mxl",length = 20)
    private String processTypeMxl;

    /**
     * 贸易方式MSL: process_type_msl
     */
    @Column(name = "process_type_msl")
    private String processTypeMsl;

    /**
     * 总毛重 : gross_weight
     */
    @Column(name = "gross_weight")
    private Double grossWeight;

    /**
     * 总净重: net_weight
     */
    @Column(name = "net_weight")
    private Double netWeight;

    /**
     * 单位净重: net_weight_each
     */
    @Column(name = "net_weight_each")
    private Integer netWeightEach;

    /**
     * 每压缩袋数量: bag_quantity
     */
    @Column(name = "bag_quantity")
    private Integer bagQuantity;

    /**
     * 每扎数量: binding_quantity
     */
    @Column(name = "binding_quantity")
    private Integer bindingQuantity;

    /**
     * 纸箱重量: box_weight
     */
    @Column(name = "box_weight")
    private Integer boxWeight;

    /**
     * 销售单位: sales_uom
     */
    @Column(name = "sales_uom",length = 3)
    private String salesUom;

    /**
     * 接缝工艺: seam_technology
     */
    @Column(name = "seam_technology",length = 2)
    private String seamTechnology;

}

