package com.tailoring.yewu.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminView {

    @GetMapping
    public String index() {
        return "admin";
    }


}