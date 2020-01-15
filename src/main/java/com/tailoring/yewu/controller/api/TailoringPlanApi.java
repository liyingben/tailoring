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
@RequestMapping("/api/tailoringPlans")
@Api(value = "计划", tags = {"计划"})
public class TailoringPlanApi {

    @Autowired
    private TailoringPlanService tailoringPlanService;



    @ResponseBody
    @RequestMapping(value = "/insertByWorkOrder", method = RequestMethod.POST)
    @ApiOperation(value = "根据工单创建计划", notes = "")
    public ActionResult insertByWorkOrder(@RequestBody List<WorkOrderDto> pos) {

        List<TailoringPlanPo> tailoringPlans = tailoringPlanService.createPlan(pos);
        if (tailoringPlans.size() > 0) {
            return new ActionResult<>(tailoringPlans);
        } else {
            return new ActionResult(ResultType.DATA_PLAN_FULL_SHARE);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "添加或更新裁剪计划 PC", notes = "修改选中行的信息，如果修改对应的计划数量，则需要判断新的计划数量大于等于对应裁剪计划已裁剪的数量之和，提示生产管理人员已裁剪数量大于计划数量")
    public ActionResult insertOrUpdate(@RequestBody List<TailoringPlanPo> pos) {

        return  tailoringPlanService.save(pos);
    }

    @ResponseBody
    @RequestMapping(value = "/updatePlanStatus", method = RequestMethod.POST)
    @ApiOperation(value = "更新裁剪计划状态 Pc", notes = "")
    public long updatePlanStatus(@RequestBody TailoringPlanStatusDto dto) {
        return tailoringPlanService.updatePlanStatus(dto.getId(), dto.getStatus());
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "更新裁剪计划状态 Pc", notes = "")
    public long delete(@RequestBody TailoringPlanStatusDto dto) {
        return tailoringPlanService.delete(dto.getId());
    }

    @ResponseBody
    @RequestMapping(value = "/updateNotFinishedPlan", method = RequestMethod.POST)
    @ApiOperation(value = "更新未完成计划", notes = "通过比对当前未完成的裁剪计划和ERP系统视图中的加工单+产品编码信息，" +
            "更新对应的计划的出货日期和计划裁剪数量；当该计划裁剪数量已经裁剪并已裁剪数量>更新后的工单数量，" +
            "则需要提示生产管理人员“该计划已经裁剪并已裁剪数量>更新后的工单数量”")

    public List<Integer> updateNotFinishedPlan(@ApiParam(name = "ids", value = "裁剪计划id列表", example = "[1,2,3]", type = "List") @RequestBody List<Integer> ids) {
        List<Integer> okIds = new ArrayList<>();
        for (Integer id : ids) {
            TailoringPlanPo po = tailoringPlanService.selectById(id);
            if (po != null) {
                okIds.add(id);
            }
        }
        return okIds;
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "裁剪计划列表 PC", notes = "注意问题点")
    public ActionResult<Page<TailoringPlanPo>> list(@RequestParam(required = false) String startTime,
                                                    @RequestParam(required = false) String endTime,
                                                    @RequestParam(required = false) String workOrderNo,
                                                    @RequestParam(required = false) String productCode,
                                                    @RequestParam(required = false) String[] status,
                                                    @RequestParam(required = false) String group,
                                                    @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC)
            Pageable pageable) {

        return new ActionResult<>(tailoringPlanService.select(startTime, endTime, workOrderNo, productCode, status,group, pageable));
    }

    @ResponseBody
    @RequestMapping(value = "/findMaxQuantity", method = RequestMethod.POST)
    @ApiOperation(value = "查询计划可以输入的最大值 PC", notes = "")
    public ActionResult findMaxQuantity(@RequestBody List<TailoringPlanPo> pos) {
        return new ActionResult(tailoringPlanService.findMaxQuantity(pos));
    }

    @ResponseBody
    @RequestMapping(value = "/findMaxChangePiecesQuantity", method = RequestMethod.POST)
    @ApiOperation(value = "查询换片可以输入的最大值 PC", notes = "")
    public ActionResult findMaxChangePiecesQuantity(@RequestBody List<TailoringPlanPo> pos) {
        return new ActionResult(tailoringPlanService.simpleMaxChangePiecesQuantity(pos));
    }
}
