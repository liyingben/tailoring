package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.service.BaseFabricDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @since 2019-10-31 16:43:27
 */
@RestController
@RequestMapping("/api/baseFabricDetails")
@Api(value = "base布料明细", tags = {"布料明细操作接口"})
public class BaseFabricDetailApi {

    @Autowired
    private BaseFabricDetailService baseFabricDetailService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "布料明细列表", tags = {"布料明细操作接口"}, notes = "注意问题点")
    public List<String> select() {
        return baseFabricDetailService.selectFabricCode();
    }
}
