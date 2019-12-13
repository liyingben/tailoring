package com.tailoring.yewu.controller.view;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @since 2019-10-31 16:43:28
 */
@Controller
@RequestMapping("/tailoringPlans")
public class TailoringPlanView {

    @GetMapping
    public String index() {
        return "TailoringPlan/index";
    }

}
