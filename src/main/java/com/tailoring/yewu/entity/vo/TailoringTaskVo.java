package com.tailoring.yewu.entity.vo;


import com.tailoring.yewu.entity.po.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
@ApiModel(value = "裁剪任务详情信息", description = "请求参数类")
public class TailoringTaskVo extends TailoringTaskPo {


    /**
     * 计划信息
     */
    public List<TailoringTaskPlanPo> plans;
    /**
     * 裁剪信息
     */
    public List<TailoringDetailPo> details;
    /**
     * 扫码信息
     */
    public List<TailoringFabricRecordPo> fabricRecords;
    /**
     * 冲销数据
     */
    public TailoringRecoveryRecordPo recoveryRecord;
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
     * 布料克重: fabric_weight
     */
    private Integer fabricWeight;
}
