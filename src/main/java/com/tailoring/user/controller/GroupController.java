package com.tailoring.user.controller;

import com.tailoring.user.model.Group;
import com.tailoring.user.service.GroupService;
import com.tailoring.yewu.common.ActionResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 监控 Controller，在线用户，手动踢出用户等功能
 * </p>
 *
 * @package: com.tailoring.user.controller
 * @description: 监控 Controller，在线用户，手动踢出用户等功能
 * @author: ben
 * @date: Created in 2018-12-11 20:55
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Slf4j
@RestController
@RequestMapping("/api/group")
@Api(value = "用户组", tags = {"用户"})
public class GroupController {
    @Autowired
    private GroupService groupService;


    @ResponseBody
    @RequestMapping(value = "findAll", method = RequestMethod.GET)
    @ApiOperation(value = "剪人员组",  notes = "得到所有裁剪组的列表")
    public ActionResult<List<Group>> findAll() {
        return new ActionResult(groupService.findAll());
    }

}
