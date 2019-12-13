package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.po.TailoringFabricLeftPo;
import com.tailoring.yewu.service.TailoringFabricLeftService;
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
@RequestMapping("/api/tailoringFabricLeft")
@Api(value="裁剪布头controller",tags={"裁剪布头操作接口"})
public class TailoringFabricLeftApi {

    @Autowired
    private TailoringFabricLeftService tailoringFabricLeftService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value="裁剪布头列表",tags={"裁剪布头操作接口"},notes="注意问题点")
    public ActionResult<List<TailoringFabricLeftPo>> select(@RequestParam String startTime, @RequestParam String endTime){
        List<TailoringFabricLeftPo> fabricLeft = tailoringFabricLeftService.selectByDate(startTime,endTime);
        return new ActionResult<>(fabricLeft);
    }
}
