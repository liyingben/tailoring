package com.tailoring.yewu.service;

import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.common.ResultType;
import com.tailoring.yewu.common.StatusEnum;
import com.tailoring.yewu.entity.dto.*;
import com.tailoring.yewu.entity.po.*;
import com.tailoring.yewu.entity.vo.TailoringExamineVo;
import com.tailoring.yewu.entity.vo.TailoringSpreadingResultVo;
import com.tailoring.yewu.entity.vo.TailoringTaskVo;
import com.tailoring.yewu.repository.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class TailoringTaskService {

    @Autowired
    TailoringTaskDao tailoringTaskDao;
    @Autowired
    BaseFabricUsageDao baseFabricUsageDao;

    @Autowired
    TailoringFabricRecordDao tailoringFabricRecordDao;

    @Autowired
    TailoringPlanDao tailoringPlanDao;
    @Autowired
    TailoringDetailDao tailoringDetailDao;
    @Autowired
    TailoringFabricConsumeDao tailoringFabricConsumeDao;
    @Autowired
    TailoringSpreadingDao tailoringSpreadingDao;
    @Autowired
    TailoringTaskPlanDao tailoringTaskPlanDao;


    /**
     * 创建任务
     * 最大可裁剪数量 >=本次裁剪数量.
     * 最大可换片数量 >=本次换片数量。
     * 以及要判断相同版型分组的件数必须相同。
     *
     * @param tailoringPlans
     * @return
     */
    @Transactional
    public ActionResult createTask(List<TailoringPlanDto> tailoringPlans) {
        Map<String, Integer> qMap = new HashMap<>();
        int maxFloor = 0;
        ResultType resultType = ResultType.OK;
        String fabricCode = null;
        String group = null;
        String member = null;
        String productCode = null;
        for (TailoringPlanDto dto : tailoringPlans) {

            TailoringPlanPo po = tailoringPlanDao.getOne(dto.getId());
            group = po.getGroup();
            member = po.getMember();
            fabricCode = po.getFabricCode();
            productCode = po.getProductCode();
            //布料号码必须统一
            if (fabricCode != null) {
                if (!fabricCode.equals(po.getFabricCode())) {
                    resultType = ResultType.GROUP_GREATER_THAN_FABRIC_CODE_ERROR;
                    break;
                }
            }
            // 比较件数是否相同
            if (!qMap.containsKey(dto.getTypeGroup())) {
                qMap.put(dto.getTypeGroup(), dto.getQuantity());
            }
            if (dto.getQuantity().intValue() != qMap.get(dto.getTypeGroup()).intValue()) {
                resultType = ResultType.GROUP_QUANTITY_ERROR;
                break;
            }

            //比较裁剪数量
            if (dto.getQuantity() > po.getQuantity()) {
                resultType = ResultType.GROUP_GREATER_THAN_MAX_TAILORING_QUANTITY_ERROR;
                break;
            } else if ((StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString().equals(po.getStatus()))) {
                if (dto.getQuantity() > po.getMaxQuantity()) {
                    resultType = ResultType.GROUP_GREATER_THAN_MAX_TAILORING_QUANTITY_ERROR;
                    break;
                }
            }
            //比较换片数量
            if (dto.getChangePiecesQuantity() > po.getChangePiecesQuantity()) {
                resultType = ResultType.GROUP_GREATER_THAN_CHANGE_PIECES_QUANTITY_ERROR;
                break;
            } else if ((StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString().equals(po.getStatus()))) {
                if (dto.getChangePiecesQuantity() > po.getMaxChangePiecesQuantity()) {
                    resultType = ResultType.GROUP_GREATER_THAN_MAX_TAILORING_QUANTITY_ERROR;
                    break;
                }
            }
            //比较层高
            maxFloor = Math.max(dto.getFloor(), maxFloor);

        }
        BaseFabricUsagePo baseFabricUsagePo = baseFabricUsageDao.findByProductCodeEquals(productCode);
        if (maxFloor > baseFabricUsagePo.getMaxFloorHeight()) {
            resultType = ResultType.GROUP_GREATER_THAN_FLOOR_ERROR;
        }

        ActionResult result =new ActionResult<>(resultType);
        if (resultType.getCode() == 200) {
            TailoringTaskPo po = new TailoringTaskPo();
            po.setGroup(group);
            po.setMember(member);
            po.setFabricCode(fabricCode);
            po.setCheckDate(new Date());
            po.setStatus(StatusEnum.TAILORING_TASK_STATUS_DEFAULT.getCode().toString());

            tailoringTaskDao.save(po);
            Map map = new HashMap();
            map.put("id",po.getId());
            result.setData(map);

            //计划保存数据保存
            for (TailoringPlanDto dto : tailoringPlans) {
                TailoringTaskPlanPo tailoringTaskPlanPo = new TailoringTaskPlanPo();
                try {
                    BeanUtils.copyProperties(tailoringTaskPlanPo, dto);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                tailoringTaskPlanPo.setId(null);
                tailoringTaskPlanPo.setTailoringPlanId(dto.getId());
                tailoringTaskPlanPo.setTaskId(po.getId());

                tailoringTaskPlanDao.save(tailoringTaskPlanPo);
            }
            if(tailoringPlans.size()>0) {
                List planIds = tailoringPlans.stream().map(TailoringPlanDto::getId).distinct().collect(Collectors.toList());
                tailoringPlanDao.updateStatus(planIds, StatusEnum.TAILORING_TASK_STATUS_START.getCode().toString());
            }
        }
        return result;
    }

    /**
     * 拉布
     *
     * @param tailoringSpreadingDto
     * @return
     */
    @Transactional
    public TailoringSpreadingResultVo spreading(TailoringSpreadingDto tailoringSpreadingDto) {

        //任务id
        Long taskId = tailoringSpreadingDto.getTaskId();

        //长度
        int quantity = tailoringSpreadingDto.getQuantity();
        //拉布次数
        int spreadingCount = tailoringSpreadingDto.getSpreadingCount();
        //卷数
        int reelCount = tailoringSpreadingDto.getFabrics().size();
        //计划数量
        int planCount = tailoringSpreadingDto.getTailoringPlans().size();
        //本次拉布长度
        int spreadingLength = quantity * spreadingCount;
        //层高
        int floor = tailoringSpreadingDto.getFloor();

        /**
         * 保存拉布信息
         */
        Long maxSpreadingId = tailoringSpreadingDao.findMaxIdByTaskId(taskId);
        TailoringSpreadingPo spreadingPo = new TailoringSpreadingPo();
        spreadingPo.setFloor(floor);
        spreadingPo.setTaskId(taskId);
        spreadingPo.setQuantity(quantity);
        spreadingPo.setSpreadingCount(spreadingCount);
        spreadingPo.setReelCount(reelCount);
        spreadingPo.setPlanCount(planCount);
        tailoringSpreadingDao.save(spreadingPo);
        Long spreadingId = spreadingPo.getId();


        /**
         * 处理布料拉布
         */
        //上次裁剪列表
        List<TailoringPlanDto> tailoringPlans = tailoringSpreadingDto.getTailoringPlans();

        //上一次布料列表
        List<TailoringFabricRecordPo> lastFabrics = getLastFabrics(taskId, maxSpreadingId);
        List<TailoringFabricRecordPo> resultFabrics = new ArrayList<>();
        List<TailoringFabricInsertDto> fabrics = tailoringSpreadingDto.getFabrics();
        for (TailoringFabricInsertDto w : fabrics) {

            TailoringFabricRecordPo last = null;
            if (lastFabrics.size() != 0) {
                List<TailoringFabricRecordPo> a = lastFabrics.stream().filter(
                        d -> w.getReelNumber().equals(d.getReelNumber())
                ).collect(Collectors.toList());

                if (a.size() == 0) {
                    continue;
                }
                last = a.get(0);
            }
            //完成的计划跳过
            if (last != null&&last.getLeftLength()<0) {
                continue;
            }
            //布料使用记录
            TailoringFabricRecordPo recordPo = new TailoringFabricRecordPo();
            try {
                BeanUtils.copyProperties(recordPo, w);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            recordPo.setId(null);
            recordPo.setSpreadingId(spreadingId);
            recordPo.setTaskId(taskId);
            recordPo.setDueDate(new Date());
            if (last != null) {
                recordPo.setTheoryLength(Double.valueOf(last.getLeftLength()));
                recordPo.setActualLength(last.getActualLength());
            } else {
                recordPo.setTheoryLength(w.getTheoryLength());
                Double actualLength = tailoringFabricRecordDao.getActualLength(w.getReelNumber());
                recordPo.setActualLength(w.getTheoryLength());
                if(actualLength!=null){
                    recordPo.setActualLength(actualLength);
                }
            }

            recordPo.setFloor(floor);
            recordPo.setSpreadingCount(spreadingCount);
            recordPo.setQuantity(quantity);
            recordPo.setSpreadingLength(spreadingLength);
            //剩余长度
            recordPo.setLeftLength((int) (recordPo.getTheoryLength() - spreadingLength));


            tailoringFabricRecordDao.save(recordPo);
            resultFabrics.add(recordPo);
        }

        /**
         * 处理裁剪
         */
        //上次裁剪列表
        List<TailoringDetailPo> tailoringDetailPos = getLastDetai(taskId, maxSpreadingId);

        //返回结果列表
        List<TailoringDetailPo> tailoringDetailPos1 = new ArrayList<>();

        //裁剪计划未完成
        List<TailoringPlanDto> tailoringPlansNew = new ArrayList<>();
        for(TailoringPlanDto dto : tailoringPlans ){
            Integer left = tailoringDetailDao.findByTaskIdEqualsAndProductCodeEqualsMinLeft(taskId, dto.getProductCode());
            if(left==null||left>0) {
                tailoringPlansNew.add(dto);
            }
        }

        Set planIds =new HashSet();
        Map<String, List<TailoringPlanDto>> tailoringPlansMap = tailoringPlansNew.stream().collect(Collectors.groupingBy(TailoringPlanDto::getTypeGroup));
        for (String typeGroup : tailoringPlansMap.keySet()) {
            List<TailoringPlanDto> planDtos = tailoringPlansMap.get(typeGroup);
            //最大可裁剪件数
            int tailoringQuantity = floor * spreadingCount * planDtos.get(0).getTypeQuantity();
            for (TailoringPlanDto dto : planDtos) {
                TailoringDetailPo last = null;
                if (tailoringDetailPos.size() != 0) {
                    List<TailoringDetailPo> a = tailoringDetailPos.stream().filter(
                            d -> dto.getProductCode().equals(d.getProductCode())
                    ).collect(Collectors.toList());
                    if (a.size() > 0) {
                        last = a.get(0);
                    }
                }

                //要这个计划要裁剪件数
                Integer spreadingQuantity = (last == null ? dto.getQuantity() + dto.getChangePiecesQuantity() : last.getLeftQuantity()) ;

                Integer leftQuantity = spreadingQuantity - tailoringQuantity;

                TailoringDetailPo detailPo = new TailoringDetailPo();

                try {
                    BeanUtils.copyProperties(detailPo, dto);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                detailPo.setId(null);
                detailPo.setTaskId(taskId);
                detailPo.setSpreadingId(spreadingId);
                detailPo.setTailoringPlanId(dto.getId());

                planIds.add(dto.getId());
                //拉布次数
                detailPo.setSpreadingCount(spreadingCount);
                //拉布件数
                detailPo.setSpreadingQuantity(Math.min(spreadingQuantity, tailoringQuantity));
                detailPo.setMainSupplement(dto.getMainSupplement());
                //成品数量
                detailPo.setProductQuantity(detailPo.getSpreadingQuantity());
                //剩余件数
                detailPo.setLeftQuantity(leftQuantity);
                detailPo.setStatus(StatusEnum.TAILORING_DETAIL_STATUS_DEFAULT.getCode().toString());
                tailoringDetailDao.save(detailPo);


                //剩余最大可裁剪件数
                tailoringQuantity = Math.abs(Math.min(leftQuantity, 0));

                //输出结果
                tailoringDetailPos1.add(detailPo);

            }
        }
        if(planIds.size()>0) {
            tailoringPlanDao.updateWorkQuantity(new ArrayList<>(planIds));
            tailoringPlanDao.updateWorkChangePiecesQuantity(new ArrayList<>(planIds));

            //任务可以提交
            try {
                TailoringTaskPo tailoringTaskPo = tailoringTaskDao.getOne(taskId);
                tailoringTaskPo.setStatus(StatusEnum.TAILORING_TASK_STATUS_START.getCode().toString());
            }catch (Exception e ){
                e.printStackTrace();
            }
        }
        //拉布结果
        TailoringSpreadingResultVo tailoringSpreadingResultVo = new TailoringSpreadingResultVo();
        tailoringSpreadingResultVo.setFabrics(resultFabrics);
        tailoringSpreadingResultVo.setTailoringDetailPo(tailoringDetailPos1);

        tailoringSpreadingResultVo.setCode(2);
        tailoringSpreadingResultVo.setSpreadingId(spreadingId);
        for(TailoringDetailPo po:tailoringDetailPos1) {
            if(po.getLeftQuantity()>0) {
                tailoringSpreadingResultVo.setCode(1);
                break;
            }else{
                // 判断如果剩余需裁剪数量不大于0并且
                // 拉布件数<=最大允许裁剪数量（最大允许裁剪数量=[Roundup((本次裁剪数量+本次换片数量）/件数)+系数]*版型分组数），
//                if(tailoringSpreadingResultVo.getCode()==2){
//                    po.getSpreadingQuantity()+po.getChangePiecesQuantity()/
//                }
            }
        }

        return tailoringSpreadingResultVo;
    }



    /**
     * 最后一次拉布裁剪详情
     *
     * @param taskId
     * @return
     */
    private List<TailoringDetailPo> getLastDetai(Long taskId, Long maxSpreadingId) {
        return tailoringDetailDao.findByTaskIdEqualsAndSpreadingIdEquals(taskId, maxSpreadingId);
    }

    /**
     * 最后一次拉布裁剪布料详情
     *
     * @param taskId
     * @return
     */
    private List<TailoringFabricRecordPo> getLastFabrics(Long taskId, Long maxSpreadingId) {

        return tailoringFabricRecordDao.findByTaskIdEqualsAndSpreadingIdEquals(taskId, maxSpreadingId);
    }

    @Transactional
    public void examine() {
        List<TailoringTaskPo> tailoringTaskPos = tailoringTaskDao.findByStatusEquals(StatusEnum.TAILORING_TASK_STATUS_START.getCode().toString());

        for (TailoringTaskPo po : tailoringTaskPos) {

            po.setCheckDate(new Date());
            po.setErpdatum(new Date());
            po.setCheckName("张三");
            po.setStatus(StatusEnum.TAILORING_TASK_STATUS_SUBMIT.getCode().toString());
            tailoringTaskDao.save(po);

            List<TailoringDetailPo> list = tailoringDetailDao.findByTaskIdEquals(po.getId());
            if(list!=null&&list.size()>0){
                List planIds = list.stream().map(TailoringDetailPo::getTailoringPlanId).distinct().collect(Collectors.toList());

                tailoringPlanDao.updateStatus(planIds,StatusEnum.TAILORING_TASK_STATUS_SUBMIT.getCode().toString());
            }


        }

    }
    /**
     * 任务详情
     *
     * @param taskId 任务id
     * @return
     */
    public TailoringTaskVo taskDetails(Long taskId) {
        TailoringTaskVo tailoringTaskVo = new TailoringTaskVo();

        TailoringTaskPo tailoringTaskPo = tailoringTaskDao.getOne(taskId);

        try {
            BeanUtils.copyProperties(tailoringTaskPo, tailoringTaskVo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        List<TailoringDetailPo> tailoringDetails = tailoringDetailDao.findByTaskIdEquals(taskId);
        tailoringTaskVo.setDetails(tailoringDetails);

        List<TailoringTaskPlanPo> tailoringTaskPlanPos = tailoringTaskPlanDao.findByTaskIdEquals(taskId);
        tailoringTaskVo.setPlans(tailoringTaskPlanPos);

        List<TailoringFabricRecordPo> tailoringFabricRecords =tailoringFabricRecordDao.findByTaskIdEquals(taskId);
        tailoringTaskVo.setFabricRecords(tailoringFabricRecords);

        return tailoringTaskVo;
    }

    /**
     * 审核详情
     *
     * @param id
     * @return
     */
    public TailoringExamineVo examineDetails(Long id) {
        TailoringDetailPo tailoringDetailPo = tailoringDetailDao.getOne(id);
        List<TailoringDetailPo> tailoringDetails = new ArrayList<>();
        tailoringDetails.add(tailoringDetailPo);
        TailoringExamineVo tailoringExamineVo = new TailoringExamineVo();
        tailoringExamineVo.setTailoringDetails(tailoringDetails);
        TailoringPlanPo tailoringPlanPo = tailoringPlanDao.getOne(tailoringDetailPo.getTailoringPlanId());
        tailoringExamineVo.setTailoringPlan(tailoringPlanPo);
        List<TailoringFabricRecordPo> tailoringFabricRecords = tailoringFabricRecordDao.findAll();
        tailoringExamineVo.setTailoringFabricRecords(tailoringFabricRecords);
        List<TailoringFabricConsumePo> tailoringFabricConsumes = tailoringFabricConsumeDao.findAll();
        tailoringExamineVo.setTailoringFabricConsumes(tailoringFabricConsumes);
        return tailoringExamineVo;
    }

    public Integer examinePass(TailoringTaskPassDto passDto) {
        TailoringTaskPo tailoringTaskPo = tailoringTaskDao.getOne(passDto.getId());
        tailoringTaskPo.setCheckName(passDto.getCheckName());
        tailoringTaskPo.setStatus(StatusEnum.TAILORING_TASK_STATUS_PASS.getCode().toString());
        tailoringTaskDao.save(tailoringTaskPo);

        List<TailoringDetailPo> tailoringDetailPos = tailoringDetailDao.findByTaskIdEquals(tailoringTaskPo.getId());


        tailoringDetailPos.forEach(w -> {
            TailoringPlanPo tailoringPlanPo = tailoringPlanDao.getOne(w.getTailoringPlanId());
            tailoringPlanPo.setStatus(StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString());
            tailoringPlanDao.save(tailoringPlanPo);
        });
        return 1;
    }

    public List<TailoringTaskPo> selectByDate(String startTime, String endTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return tailoringTaskDao.findByCreateTimeBetween(sdf.parse(startTime), sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}