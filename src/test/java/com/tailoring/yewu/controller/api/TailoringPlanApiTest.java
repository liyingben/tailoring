package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.SpringBootStartApplicationTests;
import com.tailoring.yewu.common.TailoringUtils;
import com.tailoring.yewu.entity.dto.WorkOrderDto;
import com.tailoring.yewu.entity.po.WorkOrderPo;
import com.tailoring.yewu.repository.WorkOrderDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

class TailoringPlanApiTest extends SpringBootStartApplicationTests {
    @Autowired
    TailoringPlanApi tailoringPlanApi;

    @Autowired
    WorkOrderDao workOrderDao;



    /**
     * 创建一个计划
     */
    @Test
    void createPlans() {

        WorkOrderPo workOrderPo= workOrderDao.findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoEquals("","","");
        WorkOrderDto dto =new WorkOrderDto();
        TailoringUtils.copyProperties(dto,workOrderPo);
        List<WorkOrderDto> list = new ArrayList();
        list.add(dto);
        tailoringPlanApi.insertByWorkOrder(list);

    }



    /**
     * 创建一个计划
     */
    @Test
    void fabricCodes() {
        tailoringPlanApi.fabricCodes();
    }


    /**
     * 创建一个计划
     */
    @Test
    void listForPda() {
        tailoringPlanApi.listForPda("FNA23YEA01");
    }
}