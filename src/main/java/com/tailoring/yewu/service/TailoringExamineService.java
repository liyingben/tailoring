package com.tailoring.yewu.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.common.ResultType;
import com.tailoring.yewu.common.StatusEnum;
import com.tailoring.yewu.common.TailoringUtils;
import com.tailoring.yewu.entity.dto.TailoringExaminePassDto;
import com.tailoring.yewu.entity.dto.TailoringRecoveryRecordDto;
import com.tailoring.yewu.entity.po.*;
import com.tailoring.yewu.entity.vo.TailoringExamineVo;
import com.tailoring.yewu.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TailoringExamineService {

    @Autowired
    BaseFabricDetailDao baseFabricDetailDao;
    @Autowired
    BaseFabricUsageDao baseFabricUsageDao;

    @Autowired
    TailoringTaskDao tailoringTaskDao;
    @Autowired
    TailoringFabricRecordDao tailoringFabricRecordDao;
    @Autowired
    TailoringExamineFabricRecordDao tailoringExamineFabricRecordDao;

    @Autowired
    TailoringPlanDao tailoringPlanDao;

    @Autowired
    TailoringDetailDao tailoringDetailDao;

    @Autowired
    TailoringFabricConsumeDao tailoringFabricConsumeDao;
    @Autowired
    TailoringExamineDao tailoringExamineDao;
    @Autowired
    TailoringExamineNoDao tailoringExamineNoDao;
    @Autowired
    TailoringExaminePlanDao tailoringExaminePlanDao;

    @Autowired
    TailoringTaskPlanDao tailoringTaskPlanDao;

    @Autowired
    TailoringRecoveryRecordDao tailoringRecoveryRecordDao;

    /**
     * 提交审核，
     */
    @Transactional
    public ActionResult examine() {

        //未提交的任务
        List<TailoringTaskPo> tailoringTaskPos = tailoringTaskDao.findByStatusEquals(StatusEnum.TAILORING_TASK_STATUS_START.getCode().toString());


        if(tailoringTaskPos.size()==0){
            List<Long> list = tailoringPlanDao.findByStatusEquals(StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString()).stream().map(TailoringPlanPo::getId).distinct().sorted().collect(Collectors.toList());

            if (list.size() == 0 ){
                ActionResult actionResult= new ActionResult(ResultType.EXAMINE_NO_DATA_ERROR);
                actionResult.setCode(200);
                return  actionResult;
            }else{
                tailoringPlanDao.updateStatus(list,StatusEnum.TAILORING_PLAN_STATUS_SUBMIT.getCode().toString());

                ActionResult actionResult= new ActionResult(ResultType.EXAMINE_NO_DATA_ERROR);
                actionResult.setCode(200);
                return  actionResult;
            }

        }
        //按布料编号分组任务
        Map<String, List<TailoringTaskPo>> tasksMap = tailoringTaskPos.stream().collect(Collectors.groupingBy(TailoringTaskPo::getFabricCode));

        for (String fabricCode : tasksMap.keySet()) {
            String no = genereterNo();
            BaseFabricDetailPo fabricUsagePo = baseFabricDetailDao.findFirstByFabricCodeEquals(fabricCode);
            //创建审核
            List<TailoringTaskPo> taskPos = tasksMap.get(fabricCode);

            //审核计划汇总
            List taskIds = taskPos.stream().map(TailoringTaskPo::getId).collect(Collectors.toList());
            tailoringExaminePlanDao.insertPlan(taskIds, no);

            //裁剪计划详情列表
            List<TailoringDetailPo> tailoringDetails = tailoringDetailDao.findByTaskIdIn(taskIds);
            //裁剪计划id和拉布id
            Map<Long, ArrayList<Long>> planAndSpreadingIdMap = new HashMap<>();
            for (TailoringDetailPo po : tailoringDetails) {
                if (!planAndSpreadingIdMap.containsKey(po.getTailoringPlanId())) {
                    planAndSpreadingIdMap.put(po.getTailoringPlanId(), new ArrayList<>());
                }
                planAndSpreadingIdMap.get(po.getTailoringPlanId()).add(po.getSpreadingId());
            }

            //布料使用记录列表
            List<TailoringFabricRecordPo> tailoringFabricRecords = tailoringFabricRecordDao.findByTaskIdIn(taskIds);
            Map<Long, ArrayList<TailoringFabricRecordPo>> spreadingIdFabricRecordMap = new HashMap<>();
            for (TailoringFabricRecordPo po : tailoringFabricRecords) {
                if (!spreadingIdFabricRecordMap.containsKey(po.getSpreadingId())) {
                    spreadingIdFabricRecordMap.put(po.getSpreadingId(), new ArrayList<>());
                }
                spreadingIdFabricRecordMap.get(po.getSpreadingId()).add(po);
            }

            //更新审核计划
            List<TailoringExaminePlanPo> tailoringExaminePlanPos = tailoringExaminePlanDao.findByTailoringNoEquals(no);

            for (TailoringExaminePlanPo po : tailoringExaminePlanPos) {
                BaseFabricUsagePo baseFabricUsage = baseFabricUsageDao.findByProductCodeEquals(po.getProductCode());
                if (baseFabricUsage != null) {
                    po.setTypeNumber(baseFabricUsage.getTypeNumber());
                }
                //计划数据汇总
                int spreadingLength = 0;
                List<Long> spreadingIds = planAndSpreadingIdMap.get(po.getTailoringPlanId());
                if (spreadingIds != null) {
                    for (Long spreadingId : spreadingIds) {
                        ArrayList<TailoringFabricRecordPo> fabricRecordPos = spreadingIdFabricRecordMap.get(spreadingId);
                        if (fabricRecordPos != null) {
                            for (TailoringFabricRecordPo fabricRecordPo : fabricRecordPos) {
                                spreadingLength += fabricRecordPo.getSpreadingLength();
                            }
                        }
                    }
                }
                po.setSpreadingLength(spreadingLength);
                //布料用量=（拉布长度*幅宽*层高）
                po.setConsumeQty(spreadingLength * po.getFloor() * fabricUsagePo.getFabricWidthMeter());
            }
            tailoringExaminePlanDao.saveAll(tailoringExaminePlanPos);

            //更新计划状态
            tailoringPlanDao.updateStatusByTaskIds(taskIds, StatusEnum.TAILORING_PLAN_STATUS_SUBMIT.getCode().toString());

            //布料汇总
            tailoringExamineFabricRecordDao.insertFabricRecord(taskIds, no);

            //任务状态为提交
            tailoringTaskDao.updateStatus(taskIds, StatusEnum.TAILORING_TASK_STATUS_SUBMIT.getCode().toString(), no);

            String taskIdsStr = taskIds.stream().map( i -> i.toString()).collect(Collectors.joining(",")).toString();
            //创建审核记录
            TailoringExaminePo examinePo = new TailoringExaminePo();
            examinePo.setStatus(StatusEnum.TAILORING_EXAMINE_STATUS_DEFAULT.getCode().toString());
            examinePo.setTailoringNo(no);
            examinePo.setGroup(taskPos.get(0).getGroup());
            examinePo.setMember(taskPos.get(0).getMember());
            examinePo.setFabricCode(fabricCode);
            examinePo.setFabricColour(fabricUsagePo.getFabricColour());
            examinePo.setFabricWidth(fabricUsagePo.getFabricWidthMeter());

            examinePo.setTaskIds(taskIdsStr);
            tailoringExamineDao.save(examinePo);

        }
        return new ActionResult();
    }

    /**
     * 生成流水号码
     *
     * @return
     */
    private synchronized String genereterNo() {
        TailoringExamineNoPo no = tailoringExamineNoDao.findFirstByOrderByIdDesc();
        TailoringExamineNoPo newNO = new TailoringExamineNoPo();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

        Date now = new Date();
        if (no != null && simpleDateFormat.format(now).equals(simpleDateFormat.format(no.getCreateTime()))) {
            newNO.setNumberId(no.getNumberId() + 1);
        } else {
            newNO.setNumberId(1);
        }
        newNO.setCreateTime(now);
        tailoringExamineNoDao.save(newNO);

        String year = simpleDateFormat.format(newNO.getCreateTime());

        DecimalFormat df3 = new DecimalFormat("0000");


        return year.substring(2) + "S" + df3.format(newNO.getNumberId());
    }

    /**
     * 审核详情
     *
     * @param examineId 审核id
     * @return
     */
    public TailoringExamineVo details(Long examineId) {

        TailoringExaminePo tailoringExaminePo = tailoringExamineDao.getOne(examineId);

        //提交任务列表
        List<TailoringTaskPo> taskPos = tailoringTaskDao.findByTailoringNoEquals(tailoringExaminePo.getTailoringNo());

        //任务id
        List taskIds = taskPos.stream().map(TailoringTaskPo::getId).collect(Collectors.toList());

        TailoringExamineVo tailoringExamineVo = new TailoringExamineVo();
        TailoringUtils.copyProperties(tailoringExamineVo, tailoringExaminePo);

        BaseFabricDetailPo baseFabricUsage = baseFabricDetailDao.findFirstByFabricCodeEquals(tailoringExaminePo.getFabricCode());
        if (baseFabricUsage != null) {
            tailoringExamineVo.setFabricColour(baseFabricUsage.getFabricColour());
            tailoringExamineVo.setFabricWidth(baseFabricUsage.getFabricWidthMeter());
            tailoringExamineVo.setFabricWeight(baseFabricUsage.getFabricWeight());
        }
        //裁剪计划
        List<TailoringExaminePlanPo> tailoringTaskPlanPos = tailoringExaminePlanDao.findByTailoringNoEquals(tailoringExaminePo.getTailoringNo());
        tailoringExamineVo.setPlans(tailoringTaskPlanPos);

        //布料使用记录
        List<TailoringExamineFabricRecordPo> tailoringFabricRecords = tailoringExamineFabricRecordDao.findByTailoringNoEquals(tailoringExaminePo.getTailoringNo());
        tailoringExamineVo.setFabricRecords(tailoringFabricRecords);

        //冲销
        TailoringRecoveryRecordPo tailoringRecoveryRecordPo = tailoringRecoveryRecordDao.findByTaskIdIn(taskIds);
        tailoringExamineVo.setRecoveryRecord(tailoringRecoveryRecordPo);

        return tailoringExamineVo;
    }


    /**
     * 审核通过
     *
     * @param passDto
     * @return
     */
    public Integer examinePass(TailoringExaminePassDto passDto) {
        TailoringExaminePo tailoringExaminePo = tailoringExamineDao.getOne(passDto.getId());
        if (!StatusEnum.TAILORING_EXAMINE_STATUS_DEFAULT.getCode().toString().equals(tailoringExaminePo.getStatus()))
            return 0;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        tailoringExaminePo.setStatus(StatusEnum.TAILORING_EXAMINE_STATUS_PASS.getCode().toString());
        tailoringExaminePo.setCheckName(authentication.getName());
        tailoringExaminePo.setCheckDate(new Date());
        tailoringExamineDao.save(tailoringExaminePo);

        List<TailoringExaminePlanPo> tailoringExaminePlanPos = tailoringExaminePlanDao.findByTailoringNoEquals(tailoringExaminePo.getTailoringNo());

        tailoringExaminePlanPos.forEach(w -> {
            TailoringPlanPo tailoringPlanPo = tailoringPlanDao.getOne(w.getTailoringPlanId());
            tailoringPlanPo.setStatus(StatusEnum.TAILORING_PLAN_STATUS_FINISH.getCode().toString());
            tailoringPlanDao.save(tailoringPlanPo);
        });
        return 1;
    }


    /**
     * 冲销数据
     *
     * @param dto
     */
    public void recoveryRecord(TailoringRecoveryRecordDto dto) {
        TailoringExaminePo tailoringTaskPo = tailoringExamineDao.getOne(dto.getTaskId());
        if (tailoringTaskPo == null) {
            return;
        }
        TailoringRecoveryRecordPo po = new TailoringRecoveryRecordPo();
        po.setTaskId(dto.getTaskId());
        po.setRecoveryQuantity(dto.getRecoveryQuantity());
        po.setRecoveryChangePiecesQuantity(dto.getRecoveryChangePiecesQuantity());
        tailoringRecoveryRecordDao.save(po);
    }

    /**
     * 审核列表
     *
     * @param startTime
     * @param endTime
     * @param pageable
     * @return
     */
    public Page<TailoringExaminePo> selectByDate(String startTime, String endTime, Pageable pageable) {
        Page<TailoringExaminePo> result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            QTailoringExaminePo qTailoringExaminePo = QTailoringExaminePo.tailoringExaminePo;

            BooleanExpression be = null;
            if (!StringUtils.isEmpty(startTime)) {
                be = qTailoringExaminePo.createTime.between(sdf.parse(startTime), sdf.parse(endTime));
            } else {
                be = qTailoringExaminePo.id.gt(0);
            }

            //分页
            result = tailoringExamineDao.findAll(be, pageable);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

}