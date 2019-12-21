package com.tailoring.yewu.controller.api;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkOrderApiTest {

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