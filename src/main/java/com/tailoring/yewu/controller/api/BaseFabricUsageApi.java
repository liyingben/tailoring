package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.service.BaseFabricUsageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 
 *
 * @version 1.0
 * @since 2019-10-31 16:43:27
 */
@RestController
@RequestMapping("/api/baseFabricUsage")
@Api(value="base布料用量",tags={"产品布料用量操作接口"})
public class BaseFabricUsageApi {

    @Autowired
    private BaseFabricUsageService baseFabricUsageService;


    @RequestMapping(value = "/mapTypeNumberByfabricCode", method = RequestMethod.GET)
    @ApiOperation(value="得到布料版型",notes="返回map对象，key是布料编码，")
    public ActionResult<Map> mapTypeNumberByfabricCode(){
        Map typeNumber = baseFabricUsageService.mapTypeNumberByfabricCode();
        return new ActionResult<>(typeNumber);
    }
}
