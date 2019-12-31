package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.po.BaseTailoringDistributionPo;
import com.tailoring.yewu.service.BaseTailoringDistributionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @since 2019-10-31 16:43:28
 */
@RestController
@RequestMapping("/baseTailoringDistributions")
@Api(value = "base裁剪人员组合及超省分配比例Api", tags = {"裁剪人员组合及超省分配比例操作接口"})
public class BaseTailoringDistributionApi {

    @Autowired
    private BaseTailoringDistributionService baseTailoringDistributionService;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "剪人员组合及超省分配比例列表", tags = {"裁剪人员组合及超省分配比例操作接口"}, notes = "注意问题点")
    public ActionResult<List<BaseTailoringDistributionPo>> findAll() {
        return new ActionResult(baseTailoringDistributionService.findAll());
    }
}
