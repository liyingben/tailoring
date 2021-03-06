package com.tailoring.yewu.controller.api;


import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.vo.WorkOrderVo;
import com.tailoring.yewu.service.WorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @since 2019-10-31 16:43:28
 */
@RestController
@RequestMapping("/api/workOrders")
@Api(value = "裁剪计划", tags = {"裁剪计划接口"})
public class WorkOrderApi {

    @Autowired
    private WorkOrderService workOrderService;

    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ApiOperation(value = "工单列表", notes = "注意问题点")
    public ActionResult<Page<WorkOrderVo>> workOrders(@RequestParam String startTime, @RequestParam String endTime,
                                                      @RequestParam(required = false) String fabricCode,
                                                      @RequestParam(required = false) String dept,
                                                      @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {






        Page<WorkOrderVo> orders = workOrderService.select(startTime, endTime,fabricCode,dept, pageable);
        return new ActionResult<>(orders);
    }

}
