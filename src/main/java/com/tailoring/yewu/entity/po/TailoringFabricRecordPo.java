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
    private Long taskId;

    /**
     * 拉布ID: spreading_id
     */
    @Column(name = "spreading_id")
    private Long spreadingId;

    /**
     * 布料编码: fabric_code
     */
    @Column(name = "fabric_code",length = 10)
    private String fabricCode;

    /**
     * 单次拉布长度: quantity
     */
    @Column(name = "quantity")
    private Integer quantity;

    /**
     * 布料卷号: reel_number
     */
    @Column(name = "reel_number",length = 25)
    private String reelNumber;

    /**
     * 布批号: lot_number
     */
    @Column(name = "lot_number",length = 6)
    private String lotNumber;

    /**
     * 理论长度（米）: theory_length
     */
    @Column(name = "theory_length")
    private Double theoryLength;

    /**
     * 实际长度: actual_length
     */
    @Column(name = "actual_length")
    private Double actualLength;

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
     * 理论幅宽（米）: theory_fabric_width
     */
    @Column(name = "theory_fabric_width")
    private Double theoryFabricWidth;

    /**
     * 拉布次数: spreading_count
     */
    @Column(name = "spreading_count")
    private Integer spreadingCount;

    /**
     * 换片数量: change_pieces_quantity
     */
    @ApiModelProperty(value = "换片数量", example = "4", required = true)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dueDate;


}

