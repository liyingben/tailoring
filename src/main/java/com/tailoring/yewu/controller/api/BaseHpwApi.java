package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.service.BaseHpwService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @since 2019-10-31 16:43:28
 */
@RestController
@Api(value = "base HPW表", tags = {"HPW表操作接口"})
public class BaseHpwApi {

    @Autowired
    private BaseHpwService baseHpwService;

}
