package com.tailoring.yewu.entity.vo;


import com.tailoring.yewu.common.StatusEnum;
import com.tailoring.yewu.entity.po.TailoringDetailPo;
import com.tailoring.yewu.entity.po.TailoringFabricRecordPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;


@Data
@ApiModel(value = "裁剪扫码提交返回结果", description = "请求参数类")
public class TailoringSpreadingResultVo {
    /**
     * 结果状态码
     */
    @ApiModelProperty(value = "剩余数据量>0 返回1，<0 返回2，不成立返回3,任务已经提交4", required = true)
    private Integer code;

    private StatusEnum statusEnum;
    /**
     * 拉布ID: spreading_id
     */
    @Column(name = "spreading_id")
    private Long spreadingId;

    @ApiModelProperty(value = "裁剪计划", required = true)
    private List<TailoringDetailPo> tailoringDetailPo;

    @ApiModelProperty(value = "裁剪布料", required = true)
    private List<TailoringFabricRecordPo> fabrics;

}
