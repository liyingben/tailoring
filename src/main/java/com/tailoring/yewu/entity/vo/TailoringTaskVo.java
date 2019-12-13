package com.tailoring.yewu.entity.vo;


import com.tailoring.yewu.entity.po.TailoringDetailPo;
import com.tailoring.yewu.entity.po.TailoringFabricConsumePo;
import com.tailoring.yewu.entity.po.TailoringFabricRecordPo;
import com.tailoring.yewu.entity.po.TailoringPlanPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="任务审核详情信息",description="请求参数类" )
public class TailoringTaskVo {
    /**
     * 裁剪详情
     */
    public List<TailoringDetailPo> tailoringDetails;
    /**
     * 裁剪计划
     */
    public TailoringPlanPo tailoringPlan;
    /**
     * 布料使用记录
     */
    public List<TailoringFabricRecordPo> tailoringFabricRecords;
    /**
     * 布料消耗
     */
    public List<TailoringFabricConsumePo> tailoringFabricConsumes;


}
