package com.tailoring.yewu.controller.api;


import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.common.ResultType;
import com.tailoring.yewu.common.StatusEnum;
import com.tailoring.yewu.common.TailoringUtils;
import com.tailoring.yewu.entity.dto.TailoringPlanStatusDto;
import com.tailoring.yewu.entity.dto.WorkOrderDto;
import com.tailoring.yewu.entity.po.TailoringPlanPo;
import com.tailoring.yewu.entity.po.TailoringTaskPo;
import com.tailoring.yewu.entity.vo.TailoringPlanVo;
import com.tailoring.yewu.service.TailoringPlanService;
import com.tailoring.yewu.service.TailoringTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @since 2019-10-31 16:43:28
 */
@RestController
@RequestMapping("/tailoringPlans")
@Api(value = "裁剪计划", tags = {"裁剪计划接口"})
public class TailoringPlanPdaApi {

    @Autowired
    private TailoringPlanService tailoringPlanService;
    @Autowired
    private TailoringTaskService tailoringTaskService;



    @ResponseBody
    @RequestMapping(value = "/listForPda", method = RequestMethod.GET)
    @ApiOperation(value = "裁剪计划列表  PDA", notes = "注意问题点")
    public ActionResult<List<TailoringPlanVo>> listForPda(@ApiParam(name = "fabricCode", value = "布料编码", defaultValue = "FNA15WHB03") @RequestParam(required = false) String fabricCode) {
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd");
        List<TailoringPlanPo> tailoringPlan;

        List<TailoringTaskPo> taskPos = tailoringTaskService.notSubmit();
        //如果没有传状态，先查裁剪没提交，再查询没裁剪的

        if (taskPos.size() > 0&&taskPos.get(0).getFabricCode().equals(fabricCode)) {
            tailoringPlan = tailoringPlanService.findByFabricCodeEqualsAndStatus(fabricCode, StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString());
        } else {
            tailoringPlan = tailoringPlanService.findByFabricCodeEqualsAndStatus(fabricCode, StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString());
        }

        List<TailoringPlanVo> results = new ArrayList<>();
        tailoringPlan.forEach(p -> {
            TailoringPlanVo vo = new TailoringPlanVo();
            TailoringUtils.copyProperties(vo, p);
            if(p.getDemandDate()!=null) {
                vo.setDueDateStr(sf.format(p.getDemandDate()));
            }else{
                vo.setDueDateStr(sf.format(p.getDueDate()));
            }
            results.add(vo);
        });
        return new ActionResult<>(results);
    }

    @ResponseBody
    @RequestMapping(value = "/fabricCodes", method = RequestMethod.GET)
    @ApiOperation(value = "布料编号列表 PDA", notes = "查询裁剪计划中未完成订单和未提交订单所有布料编码列表")
    public ActionResult<List<String>> fabricCodes() {
        List list = tailoringPlanService.fabricCodes();
        return new ActionResult<>(list);
    }

}
