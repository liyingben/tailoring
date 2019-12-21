package com.tailoring.yewu.controller.api;


import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.dto.TailoringFabricInsertDto;
import com.tailoring.yewu.entity.dto.TailoringPlanDto;
import com.tailoring.yewu.entity.dto.TailoringSpreadingDto;
import com.tailoring.yewu.entity.dto.TailoringTaskPassDto;
import com.tailoring.yewu.entity.po.TailoringDetailPo;
import com.tailoring.yewu.entity.po.TailoringTaskPo;
import com.tailoring.yewu.entity.vo.TailoringExamineVo;
import com.tailoring.yewu.entity.vo.TailoringSpreadingResultVo;
import com.tailoring.yewu.entity.vo.TailoringTaskVo;
import com.tailoring.yewu.service.TailoringTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @version 1.0
 * @since 2019-11-17 19:55:36
 */
@RestController
@RequestMapping("/api/tailoringTask")
@Api(value="裁剪任务",tags={"裁剪任务接口"})
public class TailoringTaskApi {

    @Autowired
    private TailoringTaskService tailoringTaskService;


    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    @ApiOperation(value="创建裁剪任务 PDA",notes=" 检查三种情况：1最大可裁剪数量 >=本次裁剪数量；" +
            " 2 最大可换片数量 >=本次换片数量；" +
            " 3以及要判断相同版型分组的件数必须相同。" +
            "返回：" +
            "800101 同版型分组的件数不同；" +
            "800102 本次裁剪数量大于最大可裁剪数量" +
            "800103 本次换片数量大于换片数量 。")
    public ActionResult createTask(@RequestBody List<TailoringPlanDto> tailoringPlans){
        ActionResult actionResult = tailoringTaskService.createTask(tailoringPlans);

        return actionResult;
    }

    @RequestMapping(value = "/spreading", method = RequestMethod.POST)
    @ApiOperation(value="拉布 PDA",notes="通过扫码增加裁剪布料")
    public ActionResult<TailoringSpreadingResultVo> spreading(@Valid @RequestBody TailoringSpreadingDto tailoringSpreadingDto){
        TailoringSpreadingResultVo result = tailoringTaskService.spreading(tailoringSpreadingDto);
        return new ActionResult<>(result);
    }
    @RequestMapping(value = "examine", method = RequestMethod.POST)
    @ApiOperation(value="添加审核",notes="也是扫码提交接口")
    public ActionResult tailoring(){
        tailoringTaskService.examine();
        return new ActionResult<>();
    }

    @RequestMapping(value = "/examineDetails", method = RequestMethod.GET)
    @ApiOperation(value="审核详情",notes="审核页面显示信息用到接口")
    public ActionResult<TailoringExamineVo> examineDetails(@RequestParam Long id){
        TailoringExamineVo result = tailoringTaskService.examineDetails(id);
        return new ActionResult<>(result);
    }

    @RequestMapping(value = "/examinePass", method = RequestMethod.POST)
    @ApiOperation(value="审核通过",notes="审核裁剪通过")
    public ActionResult<Integer> examinePass(@RequestBody TailoringTaskPassDto passDto){
        int flag= tailoringTaskService.examinePass(passDto);
        return new ActionResult<>(flag);
    }


    @RequestMapping(value = "/taskDetails", method = RequestMethod.GET)
    @ApiOperation(value="任务详情",notes="审核页面显示信息用到接口")
    public ActionResult<TailoringTaskVo> taskDetails(@RequestParam Long id){
        TailoringTaskVo result = tailoringTaskService.taskDetails(id);
        return new ActionResult<>(result);
    }


    @RequestMapping(value = "/selectByDate", method = RequestMethod.GET)
    @ApiOperation(value="任务列表列表",notes="审核的入口")
    public ActionResult<List<TailoringTaskPo>> selectByDate(@RequestParam String startTime, @RequestParam String endTime){
        List<TailoringTaskPo> result = tailoringTaskService.selectByDate(startTime,endTime);
        return new ActionResult<>(result);
    }
}
