package com.tailoring.yewu.controller.api;


import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.po.TailoringRecoveryRecordPo;
import com.tailoring.yewu.service.TailoringRecoveryRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 *
 * @version 1.0
 * @since 2019-11-17 19:55:36
 */
@RestController
@RequestMapping("/api/tailoringRecoveryRecord")
@Api(value="布料消耗记录controller",tags={"布料消耗记录操作接口"})
public class TailoringRecoveryRecordApi {

    @Autowired
    private TailoringRecoveryRecordService tailoringRecoveryRecordService;

    @RequestMapping(value = "selectByDate", method = RequestMethod.GET)
    @ApiOperation(value="布料消耗列表",notes="注意问题点")
    public ActionResult<List<TailoringRecoveryRecordPo>> selectByDate(@RequestParam String startTime, @RequestParam String endTime){
        List<TailoringRecoveryRecordPo> result = tailoringRecoveryRecordService.selectByDate(startTime,endTime);
        return new ActionResult<>(result);
    }
}
