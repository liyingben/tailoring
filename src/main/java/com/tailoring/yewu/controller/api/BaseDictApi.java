package com.tailoring.yewu.controller.api;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.dto.BaseDictDto;
import com.tailoring.yewu.service.BaseDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @version 1.0
 * @since 2019-10-31 16:43:27
 */
@RestController
@RequestMapping("/api/baseDict")
@Api(value = "base字典", tags = {"base表操作"})
public class BaseDictApi {

    @Autowired
    private BaseDictService baseDictService;

    @ResponseBody
    @RequestMapping(value = "/addKey", method = RequestMethod.POST)
    @ApiOperation(value = "添加字典 PC", notes = "字典接口")
    public ActionResult addKey(@Valid @RequestBody BaseDictDto dto) {
        baseDictService.addKey(dto);
        return new ActionResult();
    }

    @RequestMapping(value = "/getKey", method = RequestMethod.GET)
    @ApiOperation(value = "获得字典值 PC", notes = "得到字典里的一个字符串")
    public ActionResult<String> getKey(@RequestParam String key) {
        return new ActionResult<>(baseDictService.getValue(key));
    }
}
