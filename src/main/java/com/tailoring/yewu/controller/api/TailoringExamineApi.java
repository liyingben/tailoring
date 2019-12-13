package com.tailoring.yewu.controller.api;


import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.dto.TailoringTaskPassDto;
import com.tailoring.yewu.entity.po.TailoringTaskPo;
import com.tailoring.yewu.entity.vo.TailoringTaskVo;
import com.tailoring.yewu.service.TailoringTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 *
 * @version 1.0
 * @since 2019-11-17 19:55:36
 */
@RestController
@RequestMapping("/api/tailoringExamine")
@Api(value="裁剪controller",tags={"审核操作接口"})
public class TailoringExamineApi {

    @Autowired
    private TailoringTaskService tailoringTaskService;


    @RequestMapping(value = "examine", method = RequestMethod.POST)
    @ApiOperation(value="添加审核",notes="也是扫码提交接口")
    public ActionResult tailoring(){
        tailoringTaskService.examine();
        return new ActionResult<>();
    }

    @RequestMapping(value = "/selectByDate", method = RequestMethod.GET)
    @ApiOperation(value="审核列表",notes="通过扫码增加裁剪布料")
    public ActionResult<List<TailoringTaskPo>> selectByDate(@RequestParam String startTime, @RequestParam String endTime){

        List<TailoringTaskPo> result = tailoringTaskService.selectByDate(startTime,endTime);
        return new ActionResult<>(result);
    }

    @RequestMapping(value = "/examineDetails", method = RequestMethod.GET)
    @ApiOperation(value="审核详情",notes="审核页面显示信息用到接口")
    public ActionResult<TailoringTaskVo> examineDetails(@RequestParam Long id){
        TailoringTaskVo result = tailoringTaskService.examineDetails(id);
        return new ActionResult<>(result);
    }

    @RequestMapping(value = "/examinePass", method = RequestMethod.POST)
    @ApiOperation(value="审核通过",notes="审核裁剪通过")
    public ActionResult<Integer> examinePass(@RequestBody TailoringTaskPassDto passDto){
        int flag= tailoringTaskService.examinePass(passDto);
        return new ActionResult<>(flag);
    }
}
