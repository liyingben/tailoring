package com.tailoring.yewu.controller.api;


import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.dto.TailoringExaminePassDto;
import com.tailoring.yewu.entity.dto.TailoringRecoveryRecordDto;
import com.tailoring.yewu.entity.po.TailoringExaminePo;
import com.tailoring.yewu.entity.vo.TailoringExamineVo;
import com.tailoring.yewu.service.TailoringExamineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 审核
 *
 * @version 1.0
 * @since 2019-11-17 19:55:36
 */
@RestController
@RequestMapping("/api/tailoringExamine")
@Api(value = "审核", tags = {"审核"})
public class TailoringExamineApi {

    @Autowired
    private TailoringExamineService tailoringExamineService;


    @RequestMapping(value = "examine", method = RequestMethod.POST)
    @ApiOperation(value = "添加审核", notes = "也是扫码提交接口")
    public ActionResult tailoring() {
        return tailoringExamineService.examine();
    }

    @RequestMapping(value = "/examinePass", method = RequestMethod.POST)
    @ApiOperation(value = "审核通过", notes = "审核裁剪通过")
    public ActionResult<Integer> examinePass(@RequestBody TailoringExaminePassDto passDto) {
        int flag = tailoringExamineService.examinePass(passDto);
        return new ActionResult<>(flag);
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    @ApiOperation(value = "审核详情", notes = "审核页面显示信息用到接口")
    public ActionResult<TailoringExamineVo> taskDetails(@RequestParam Long id) {
        TailoringExamineVo result = tailoringExamineService.details(id);
        return new ActionResult<>(result);
    }

    @RequestMapping(value = "/recoveryRecord", method = RequestMethod.GET)
    @ApiOperation(value = "冲销审核", notes = "审核页面显示信息用到接口")
    public ActionResult<TailoringExamineVo> recoveryRecord(@RequestBody TailoringRecoveryRecordDto dto) {
        tailoringExamineService.recoveryRecord(dto);
        return new ActionResult<>();
    }

    @RequestMapping(value = "/selectByDate", method = RequestMethod.GET)
    @ApiOperation(value = "审核列表列表", notes = "审核的入口")
    public ActionResult<Page<TailoringExaminePo>> selectByDate(@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime, Pageable pageable) {
        Page<TailoringExaminePo> result = tailoringExamineService.selectByDate(startTime, endTime, pageable);
        return new ActionResult<>(result);
    }
}
