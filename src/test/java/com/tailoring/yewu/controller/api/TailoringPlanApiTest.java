package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.SpringBootYewuApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class TailoringPlanApiTest extends SpringBootYewuApplicationTests {
    @Autowired
    TailoringPlanApi tailoringPlanApi;
    @Test
    void select() {

        tailoringPlanApi.select("FNA20WHA02",null);

        tailoringPlanApi.select("FNA40GRA02",null);
    }
}