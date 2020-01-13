package com.tailoring.yewu.controller.api;


import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.common.ResultType;
import com.tailoring.yewu.entity.dto.TailoringSpreadingDto;
import com.tailoring.yewu.entity.dto.TailoringTaskPlanDto;
import com.tailoring.yewu.entity.po.TailoringTaskPo;
import com.tailoring.yewu.entity.vo.TailoringSpreadingResultVo;
import com.tailoring.yewu.entity.vo.TailoringTaskVo;
import com.tailoring.yewu.service.TailoringTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @since 2019-11-17 19:55:36
 */
@RestController
@RequestMapping("/api/tailoringTask")
@Api(value = "裁剪任务", tags = {"裁剪任务接口"})
public class TailoringTaskApi {

    @Autowired
    private TailoringTaskService tailoringTaskService;

    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    @ApiOperation(value = "创建裁剪任务 PDA", notes = " 检查三种情况：" +
            " 1 最大可裁剪数量 >=本次裁剪数量；" +
            " 2 最大可换片数量 >=本次换片数量；" +
            " 3 以及要判断相同版型分组的件数必须相同。" +
            "返回：" +
            " 800101 同版型分组的件数不同；" +
            " 800102 本次裁剪数量大于最大可裁剪数量" +
            " 800103 本次换片数量大于换片数量 。")
    public ActionResult createTask(@RequestBody List<TailoringTaskPlanDto> tailoringPlans) {

        ResultType resultType = tailoringTaskService.checkTailoringPlans(tailoringPlans);
        TailoringTaskPo tailoringTaskPo = tailoringTaskService.createTask(tailoringPlans);

        ActionResult result = new ActionResult<>(resultType);
        if (resultType.getCode() == 200) {
            Map map = new HashMap();
            map.put("id", tailoringTaskPo.getId());
            result.setData(map);
        }
        return result;
    }

    @RequestMapping(value = "/spreading", method = RequestMethod.POST)
    @ApiOperation(value = "拉布 PDA", notes = "扫码保存")
    public ActionResult<TailoringSpreadingResultVo> spreading(@Valid @RequestBody TailoringSpreadingDto tailoringSpreadingDto) {
        TailoringSpreadingResultVo result = tailoringTaskService.spreading(tailoringSpreadingDto);
        result.setCode(result.getStatusEnum().getCode());
        ActionResult actionResult = new ActionResult<>(result);
        actionResult.setMessage(result.getStatusEnum().getDesc());
        return actionResult;
    }

    @RequestMapping(value = "/taskDetails", method = RequestMethod.GET)
    @ApiOperation(value = "任务详情", notes = "审核页面显示信息用到接口")
    public ActionResult<TailoringTaskVo> taskDetails(@RequestParam Long id) {
        TailoringTaskVo result = tailoringTaskService.taskDetails(id);
        return new ActionResult<>(result);
    }

    @RequestMapping(value = "/selectByDate", method = RequestMethod.GET)
    @ApiOperation(value = "任务列表列表", notes = "审核的入口")
    public ActionResult<Page<TailoringTaskPo>> selectByDate(@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime, Pageable pageable) {
        Page<TailoringTaskPo> result = tailoringTaskService.selectByDate(startTime, endTime, pageable);
        return new ActionResult<>(result);
    }
}
