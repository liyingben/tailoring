package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.dto.TailoringFabricLeftDto;
import com.tailoring.yewu.entity.po.TailoringDetailPo;
import com.tailoring.yewu.entity.vo.TailoringFabricLeftTheoryLengthVo;
import com.tailoring.yewu.service.TailoringDetailService;
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
@RequestMapping("/tailoring")
@Api(value = "裁剪", tags = {"裁剪操作接口"})
public class TailoringDetailApi {

    @Autowired
    private TailoringDetailService tailoringDetailService;

    @Autowired
    private TailoringFabricLeftService tailoringFabricLeftService;
    @Autowired
    private TailoringExamineService tailoringExamineService;


    @RequestMapping(value = "examine", method = RequestMethod.POST)
    @ApiOperation(value = "扫码提交审核 PDA", notes = "也是扫码提交接口")
    public ActionResult examine() {

        return tailoringExamineService.examine();
    }

    @RequestMapping(value = "toFabricLeft", method = RequestMethod.POST)
    @ApiOperation(value = "扫码删除 PDA", notes = "交扫描布匹写入到布头表")
    public ActionResult toFabricLeft(@Valid @RequestBody List<TailoringFabricLeftDto> list) {
        tailoringFabricLeftService.save(list);
        return new ActionResult<>();
    }


    @RequestMapping(value = "fabricLeftTheoryLength", method = RequestMethod.GET)
    @ApiOperation(value = "等到布头的理论长度 PDA", notes = "得到布头表的长度")
    public ActionResult<TailoringFabricLeftTheoryLengthVo> fabricLeftTheoryLength(String reelNumber) {
        List fabricLefts = tailoringFabricLeftService.getFabricLefts(reelNumber);

        TailoringFabricLeftTheoryLengthVo vo = new TailoringFabricLeftTheoryLengthVo();
        vo.setFagEndList(fabricLefts);
        vo.setTheoryLength(tailoringFabricLeftService.getTheoryLength(reelNumber));
        return new ActionResult<>(vo);
    }


    @RequestMapping(value = "/selectByDate", method = RequestMethod.GET)
    @ApiOperation(value = "裁剪明细列表 PC", notes = "通过扫码增加裁剪布料")
    public ActionResult<List<TailoringDetailPo>> selectByDate(@RequestParam String startTime, @RequestParam String endTime) {
        List<TailoringDetailPo> result = tailoringDetailService.selectByDate(startTime, endTime);
        return new ActionResult<>(result);
    }

}
