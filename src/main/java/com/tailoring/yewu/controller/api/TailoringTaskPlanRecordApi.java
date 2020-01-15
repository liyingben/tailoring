package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.dto.TailoringFabricLeftDto;
import com.tailoring.yewu.entity.dto.TailoringFabricLeftsDto;
import com.tailoring.yewu.entity.po.TailoringTaskPlanRecordPo;
import com.tailoring.yewu.entity.vo.TailoringFabricLeftTheoryLengthVo;
import com.tailoring.yewu.service.TailoringTaskPlanRecordService;
import com.tailoring.yewu.service.TailoringExamineService;
import com.tailoring.yewu.service.TailoringFabricLeftService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @version 1.0
 * @since 2019-11-17 19:55:36
 */
@RestController
@RequestMapping("api/tailoringTaskPlanRecord")
@Api(value = "裁剪", tags = {"计划"})
public class TailoringTaskPlanRecordApi {

    @Autowired
    private TailoringTaskPlanRecordService tailoringDetailService;


    @RequestMapping(value = "/selectByDate", method = RequestMethod.GET)
    @ApiOperation(value = "裁剪计划明细列表 PC", notes = "通过扫码增加裁剪布料")
    public ActionResult<List<TailoringTaskPlanRecordPo>> selectByDate(@RequestParam String startTime, @RequestParam String endTime) {
        List<TailoringTaskPlanRecordPo> result = tailoringDetailService.selectByDate(startTime, endTime);
        return new ActionResult<>(result);
    }

}
