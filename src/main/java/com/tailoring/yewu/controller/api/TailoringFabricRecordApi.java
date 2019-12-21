package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.po.TailoringFabricRecordPo;
import com.tailoring.yewu.service.TailoringFabricRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//import com.tailoring.yewu.mapper.TailoringFabricRecordPoDtoMapper;

/**
 * 
 *
 * @version 1.0
 * @since 2019-11-25 19:55:36
 */
@RestController
@RequestMapping("/api/tailoringFabricRecord")
@Api(value="布料使用记录controller",tags={"布料使用记录操作接口"})
public class TailoringFabricRecordApi {

    @Autowired
    private TailoringFabricRecordService tailoringFabricRecordService;



    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value="删除布料",notes="注意问题点")
    public ActionResult<Integer> delete(Integer id) {
        return new ActionResult<>(tailoringFabricRecordService.delete(id));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value="布料使用记录列表",notes="注意问题点")
    public ActionResult<List<TailoringFabricRecordPo>> select(@RequestParam String fabricCode, @RequestParam String lotNumber, @RequestParam String reelNumber){
        TailoringFabricRecordPo query = new TailoringFabricRecordPo();
        query.setFabricCode(fabricCode);
        query.setLotNumber(lotNumber);
        query.setReelNumber(reelNumber);
        List<TailoringFabricRecordPo> fabricLeft = tailoringFabricRecordService.select(query);
//        List<TailoringFabricRecordDto>  results = new ArrayList();
//        fabricLeft.forEach(p -> {
//            TailoringFabricRecordDto dto = new TailoringFabricRecordDto();
//            try {
//                BeanUtils.copyProperties(dto, p);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//            results.add(dto);
//        });
        return new ActionResult<>(fabricLeft);
    }


    @RequestMapping(value = "selectByDate", method = RequestMethod.GET)
    @ApiOperation(value="布料使用记录列表",notes="注意问题点")
    public ActionResult<List<TailoringFabricRecordPo>> selectByDate(@RequestParam String startTime, @RequestParam String endTime){
        List<TailoringFabricRecordPo> fabricLeft = tailoringFabricRecordService.selectByDate(startTime,endTime);
        return new ActionResult<>(fabricLeft);
    }
}
