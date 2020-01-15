package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.dto.TailoringFabricLeftDto;
import com.tailoring.yewu.entity.dto.TailoringFabricLeftsDto;
import com.tailoring.yewu.entity.po.TailoringTaskPlanRecordPo;
import com.tailoring.yewu.entity.vo.TailoringFabricLeftTheoryLengthVo;
import com.tailoring.yewu.service.TailoringExamineService;
import com.tailoring.yewu.service.TailoringFabricLeftService;
import com.tailoring.yewu.service.TailoringTaskPlanRecordService;
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
@RequestMapping("/tailoring")
public class TailoringPdaApi {

    @Autowired
    private TailoringFabricLeftService tailoringFabricLeftService;

    @Autowired
    private TailoringExamineService tailoringExamineService;


    @RequestMapping(value = "examine", method = RequestMethod.POST)
    @ApiOperation(value = "扫码提交审核 PDA",tags = "PDA", notes = "也是扫码提交接口")
    public ActionResult examine() {

        return tailoringExamineService.examine();
    }

    @RequestMapping(value = "toFabricLeft", method = RequestMethod.POST)
    @ApiOperation(value = "保存到布头 PDA", tags = "PDA" ,notes = "交扫描布匹写入到布头表")
    public ActionResult toFabricLeft(@Valid @RequestBody List<TailoringFabricLeftDto> list) {
       return  tailoringFabricLeftService.save(list);
    }

    @RequestMapping(value = "toFabricLeft2", method = RequestMethod.POST)
    @ApiOperation(value = "保存到布头2 PDA", tags = "PDA" ,notes = "交扫描布匹写入到布头表")
    public ActionResult toFabricLeft2(@Valid @RequestBody TailoringFabricLeftsDto dto) {
        return  tailoringFabricLeftService.save(dto);
    }

    @RequestMapping(value = "fabricLeftTheoryLength", method = RequestMethod.GET)
    @ApiOperation(value = "等到布头的理论长度 PDA",tags = "PDA" , notes = "得到布头表的长度")
    public ActionResult<TailoringFabricLeftTheoryLengthVo> fabricLeftTheoryLength(String reelNumber) {
        return new ActionResult<>(tailoringFabricLeftService.getTheoryLengthAndLeftList(reelNumber));
    }


}
