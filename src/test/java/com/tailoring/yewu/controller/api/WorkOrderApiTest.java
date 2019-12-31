package com.tailoring.yewu.controller.api;

import cn.hutool.core.util.IdUtil;
import com.tailoring.yewu.SpringBootStartApplicationTests;
import com.tailoring.yewu.service.WorkOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class WorkOrderApiTest  extends SpringBootStartApplicationTests {
    @Autowired
    private WorkOrderService workOrderService;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void workOrders() {

        String salt = IdUtil.fastSimpleUUID();
        System.out.println(salt);

        //c60b85b8c5c94d97ba5d2ce729c07ae1
    }
}