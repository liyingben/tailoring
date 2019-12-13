package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "tailoring_fabric_record")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringFabricRecordPo extends AbstractAuditModel {

    /**
     * 任务ID: task_id
     */
    @Column(name = "task_id")
    private Integer taskId;

    /**
     * 裁剪明细ID: tailoring_detail_id
     */
    @Column(name = "tailoring_detail_id")
    private Integer tailoringDetailId;

    /**
     * NO: sort_no
     */
    @ApiModelProperty(value="NO:",example="4A-9253",required = true)
    @Column(name = "sort_no")
    private String sortNo;

    /**
     * 组别: group
     */
    @ApiModelProperty(value="组别",example="A3",required = true)
    @Column(name="`group`")
    private String group;

    /**
     * 组员: member
     */
    @ApiModelProperty(value="组员",example="郑德敏,陈善云,",required = true)
    private String member;


    /**
     * 布料编码: fabric_code
     */
    @Column(name = "fabric_code")
    private String fabricCode;
    /**
     * 版型分组: type_group
     */
    @Column(name = "type_group")
    private String typeGroup;
    /**
     * 工单号: work_order_no
     */
    @ApiModelProperty(value="工单号",example="4A-9253",required = true)
    @Column(name = "work_order_no")
    private String workOrderNo;

    /**
     * 产品行号: product_line_no
     */
    @ApiModelProperty(value="产品行号",example="30000",required = true)
    @Column(name = "product_line_no")
    private Integer productLineNo;

    /**
     * 产品编码: product_code
     */
    @ApiModelProperty(value="产品编码",example="10000",required = true)
    @Column(name = "product_code")
    private String productCode;


    /**
     * 扎单数量: binding_quantity
     */
    @ApiModelProperty(value="扎单数量",example="12",required = true)
    @Column(name = "binding_quantity")
    private Integer bindingQuantity;

    /**
     * 成品数量: product_quantity
     */
    @Column(name = "product_quantity")
    private Integer productQuantity;
    /**
     * 排版件数: pieces_number
     */
    @Column(name = "pieces_number")
    private Integer piecesNumber;

    /**
     * 总长度（米）: sum_length
     */
    @Column(name = "sum_length")
    private Double sumLength;

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
     * 理论长度（米）: theory_length
     */
    @Column(name = "theory_length")
    private Double theoryLength;

    /**
     * 理论幅宽（米）: theory_fabric_width
     */
    @Column(name = "theory_fabric_width")
    private Double theoryFabricWidth;

    /**
     * 拉布长度（米）: spreading_length
     */
    @Column(name = "spreading_length")
    private Integer spreadingLength;

    /**
     * 层数（层高）: floor
     */
    private Integer floor;

    /**
     * 实际幅宽: actual_fabric_width
     */
    @Column(name = "actual_fabric_width")
    private Double actualFabricWidth;
    /**
     * 拉布次数: spreading_count
     */
    @Column(name = "spreading_count")
    private Integer spreadingCount;

    /**
     * 换片数量: change_pieces_quantity
     */
    @ApiModelProperty(value="换片数量",example="4",required = true)
    @Column(name = "change_pieces_quantity")
    private Integer changePiecesQuantity;


    /**
     * 剩余长度（米）: left_length
     */
    @Column(name = "left_length")
    private Integer leftLength;

    /**
     * 出货日期
     */
    @Column(name = "due_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING,pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date dueDate;

}

