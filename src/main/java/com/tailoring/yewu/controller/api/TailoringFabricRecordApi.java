package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.dto.TailoringFabricRecordDto;
import com.tailoring.yewu.entity.po.TailoringFabricRecordPo;
import com.tailoring.yewu.service.TailoringFabricRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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

//    @Autowired
//    public TailoringFabricRecordPoDtoMapper tailoringFabricRecordPoDtoMapper;

    @RequestMapping(value = "/inserts", method = RequestMethod.POST)
    @ApiOperation(value="添加布料使用记录,扫码接口",notes="注意问题点")
    public ActionResult<List<Long>> inserts(@RequestBody List<TailoringFabricRecordDto> dtos) {

       List<Long> ids = new ArrayList<>();
       for(TailoringFabricRecordDto dto : dtos){
           TailoringFabricRecordPo po = new TailoringFabricRecordPo();
           try {
               BeanUtils.copyProperties(po, dto);
           } catch (IllegalAccessException e) {
               e.printStackTrace();
           } catch (InvocationTargetException e) {
               e.printStackTrace();
           }
//           TailoringFabricRecordPo po = tailoringFabricRecordPoDtoMapper.t2S(dto);
           long id = tailoringFabricRecordService.insert(po);
           ids.add(id);
       }
        return new ActionResult<>(ids);
    }

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
