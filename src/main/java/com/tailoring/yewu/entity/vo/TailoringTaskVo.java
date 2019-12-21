package com.tailoring.yewu.entity.vo;


import com.tailoring.yewu.entity.po.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value="裁剪任务详情信息",description="请求参数类" )
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


}
