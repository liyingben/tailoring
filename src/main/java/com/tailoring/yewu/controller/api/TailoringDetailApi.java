package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.common.ResultType;
import com.tailoring.yewu.entity.dto.TailoringFabricInsertDto;
import com.tailoring.yewu.entity.dto.TailoringInsertDto;
import com.tailoring.yewu.entity.dto.TailoringPlanDto;
import com.tailoring.yewu.entity.po.TailoringDetailPo;
import com.tailoring.yewu.entity.vo.TailoringDetailResultVo;
import com.tailoring.yewu.service.TailoringDetailService;
import com.tailoring.yewu.service.TailoringTaskService;
import com.tailoring.yewu.service.TailoringFabricLeftService;
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
@RequestMapping("/tailoring")
@Api(value="裁剪",tags={"裁剪操作接口"})
public class TailoringDetailApi {

    @Autowired
    private TailoringDetailService tailoringDetailService;
    @Autowired
    private TailoringTaskService tailoringTaskService;
    @Autowired
    private TailoringFabricLeftService tailoringFabricLeftService;
    @RequestMapping(value = "/checkDetail", method = RequestMethod.POST)
    @ApiOperation(value="裁剪布料 PDA",notes=" 检查三种情况：1最大可裁剪数量 >=本次裁剪数量；" +
            " 2 最大可换片数量 >=本次换片数量；\n" +
            " 3以及要判断相同版型分组的件数必须相同。" +
            "返回：\n" +
            "800101 同版型分组的件数不同；" +
            "800102 本次裁剪数量大于最大可裁剪数量；" +
            "800103 本次换片数量大于换片数量 。")
    public ActionResult checkDetail(@RequestBody List<TailoringPlanDto> tailoringPlans){
        ResultType result = tailoringDetailService.checkDetail(tailoringPlans);
        return new ActionResult<>(result);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(value="裁剪布料 PDA",notes="通过扫码增加裁剪布料")
    public ActionResult<TailoringDetailResultVo> detail(@RequestBody TailoringInsertDto tailoringInsertDto){
        TailoringDetailResultVo result = tailoringDetailService.detail(tailoringInsertDto);
        return new ActionResult<>(result);
    }

    @RequestMapping(value = "examine", method = RequestMethod.POST)
    @ApiOperation(value="扫码提交审核 PDA",notes="也是扫码提交接口")
    public ActionResult tailoring(){
        tailoringTaskService.examine();
        return new ActionResult<>();
    }

    @RequestMapping(value = "toFabricLeft", method = RequestMethod.POST)
    @ApiOperation(value="扫码删除 PDA",notes="交扫描布匹写入到布头表")
    public ActionResult toFabricLeft(@RequestBody List<TailoringFabricInsertDto> list){
        tailoringFabricLeftService.save(list);
        return new ActionResult<>();
    }


    @RequestMapping(value = "fabricLeftTheoryLength", method = RequestMethod.GET)
    @ApiOperation(value="等到布头的理论长度 PDA",notes="得到布头表的长度")
    public ActionResult<Double> fabricLeftTheoryLength(String reelNumber){
        return new ActionResult<>(tailoringFabricLeftService.getTheoryLength(reelNumber));
    }


    @RequestMapping(value = "/selectByDate", method = RequestMethod.GET)
    @ApiOperation(value="裁剪明细列表 PC",notes="通过扫码增加裁剪布料")
    public ActionResult<List<TailoringDetailPo>> selectByDate(@RequestParam String startTime, @RequestParam String endTime){
        List<TailoringDetailPo> result = tailoringDetailService.selectByDate(startTime,endTime);
        return new ActionResult<>(result);
    }

}
