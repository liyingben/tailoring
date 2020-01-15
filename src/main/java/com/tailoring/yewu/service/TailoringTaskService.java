package com.tailoring.yewu.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.tailoring.user.util.SecurityUtil;
import com.tailoring.user.vo.UserPrincipal;
import com.tailoring.yewu.common.ResultType;
import com.tailoring.yewu.common.StatusEnum;
import com.tailoring.yewu.common.TailoringUtils;
import com.tailoring.yewu.entity.dto.TailoringFabricInsertDto;
import com.tailoring.yewu.entity.dto.TailoringSpreadingDto;
import com.tailoring.yewu.entity.dto.TailoringTaskPlanDto;
import com.tailoring.yewu.entity.po.*;
import com.tailoring.yewu.entity.vo.TailoringSpreadingResultVo;
import com.tailoring.yewu.entity.vo.TailoringTaskPlanVo;
import com.tailoring.yewu.entity.vo.TailoringTaskVo;
import com.tailoring.yewu.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TailoringTaskService {
    @Autowired
    BaseFabricDetailDao baseFabricDetailDao;
    @Autowired
    BaseFabricUsageDao baseFabricUsageDao;

    @Autowired
    TailoringTaskDao tailoringTaskDao;

    @Autowired
    TailoringFabricRecordDao tailoringFabricRecordDao;

    @Autowired
    TailoringPlanDao tailoringPlanDao;

    @Autowired
    TailoringOrderDao tailoringOrderDao;

    @Autowired
    TailoringTaskPlanRecordDao tailoringTaskPlanRecordDao;

    @Autowired
    TailoringFabricConsumeDao tailoringFabricConsumeDao;

    @Autowired
    TailoringSpreadingDao tailoringSpreadingDao;

    @Autowired
    TailoringTaskPlanDao tailoringTaskPlanDao;


    @Autowired
    TailoringRecoveryRecordDao tailoringRecoveryRecordDao;
    @Autowired
    private BaseDictDao baseDictDao;

    /**
     * 检查裁剪输入是否有问题
     * 最大可裁剪数量 >= 本次裁剪数量.
     * 最大可换片数量 >= 本次换片数量。
     * 以及要判断相同版型分组的件数必须相同。
     * @param tailoringPlans
     * @return
     */
    public ResultType checkTailoringPlans(List<TailoringTaskPlanDto> tailoringPlans){
        Map<String, Integer> qMap = new HashMap<>();
        int maxFloor = 0;
        ResultType resultType = ResultType.OK;
        if(tailoringPlans.size()==0){
            return ResultType.CREATE_TASK_NO_SELECT_PLAN_ERROR;
        }

        List<TailoringTaskPo> taskPos = notSubmit();
        //如果没有传状态，先查裁剪没提交，再查询没裁剪的
        if (taskPos.size() > 0) {
           return ResultType.GROUP_GREATER_THAN_SUBMIT_ERROR;
        }

        TailoringTaskPlanDto first = tailoringPlans.get(0);
        String fabricCode = first.getFabricCode();
        String productCode = first.getProductCode();


        for (TailoringTaskPlanDto dto : tailoringPlans) {

            TailoringPlanPo po = tailoringPlanDao.getOne(dto.getId());
            //请把已保存的计划提交给车间主任审核
            if (StatusEnum.TAILORING_PLAN_STATUS_SUBMIT.getCode().toString().equals(po.getStatus())) {
                resultType = ResultType.GROUP_GREATER_THAN_SUBMIT_ERROR;
                break;
            }
            //布料号码必须统一
            if (fabricCode != null) {
                if (!fabricCode.equals(po.getFabricCode())) {
                    resultType = ResultType.GROUP_GREATER_THAN_FABRIC_CODE_ERROR;
                    break;
                }
            }
            // 比较件数是否相同
            if (!qMap.containsKey(dto.getTypeGroup())) {
                qMap.put(dto.getTypeGroup(), dto.getTypeQuantity());
            }
            if (dto.getTypeQuantity().intValue() != qMap.get(dto.getTypeGroup()).intValue()) {
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
//            maxFloor = Math.max(dto.getFloor(), maxFloor);
            maxFloor += dto.getFloor();

        }
        // 大于最大层高
        BaseFabricUsagePo baseFabricUsagePo = baseFabricUsageDao.findByProductCodeEquals(productCode);
        if (maxFloor > baseFabricUsagePo.getMaxFloorHeight()) {
            resultType = ResultType.GROUP_GREATER_THAN_FLOOR_ERROR;
        }

        return resultType;
    }
    /**
     * 创建任务
     * @param tailoringPlans
     * @return
     */
    @Transactional
    public TailoringTaskPo createTask(List<TailoringTaskPlanDto> tailoringPlans) {

        TailoringPlanPo tailoringPlanPo = tailoringPlanDao.getOne(tailoringPlans.get(0).getId());
        String fabricCode = tailoringPlanPo.getFabricCode();
        String group = tailoringPlanPo.getGroup();
        Long groupid = tailoringPlanPo.getGroupId();
        String member = tailoringPlanPo.getMember();
        BaseFabricDetailPo baseFabricDetailPo = baseFabricDetailDao.findFirstByFabricCodeEquals(fabricCode);
        //最大允许裁剪数量系数
        double maxAllowTailoringCoefficient = 1;
        BaseDictPo dict = baseDictDao.findByKeyEquals("maxAllowTailoringCoefficient");
        if (dict != null) {
            try {
                maxAllowTailoringCoefficient = Double.valueOf(dict.getValue());
            } catch (Exception e) {
                //系数异常用默认
            }
        }
        TailoringTaskPo po = new TailoringTaskPo();
        po.setGroup(group);
        po.setGroupId(groupid);
        po.setMember(member);
        po.setFabricCode(fabricCode);
        po.setStatus(StatusEnum.TAILORING_TASK_STATUS_DEFAULT.getCode().toString());
        po.setIsControlFabric(baseFabricDetailPo.getIsControlFabric());
        UserPrincipal userPrincipal = SecurityUtil.getCurrentUser();
        if(userPrincipal!=null) {
            po.setUserId(userPrincipal.getId());
        }


        //布匹号是否要求一致
        po.setIsControlFabric(baseFabricDetailPo.getIsControlFabric());

        tailoringTaskDao.save(po);


        //计划保存数据保存
        for (TailoringTaskPlanDto dto : tailoringPlans) {
            TailoringTaskPlanPo tailoringTaskPlanPo = new TailoringTaskPlanPo();
            TailoringUtils.copyProperties(tailoringTaskPlanPo, dto);
            tailoringTaskPlanPo.setId(null);
            tailoringTaskPlanPo.setTailoringPlanId(dto.getId());
            tailoringTaskPlanPo.setTaskId(po.getId());
            /**
             *
             当输入拉布次数并计算出理论长度时，
             计算“实际长度”（实际长度=层高*长度）。层高=拉布次数*卷数。计算“理论长度”。上一次的理论长度-实际长度。
             1，检查实际长度>理论长度+差异系数时，提示用户：拉布长度超出理论长度太多，请检查数据是否正确，如果点"是"，则保存对应的记录。如果点“否”则返回到扫码界面输入界面，员工修正数据后点击保存后再进行检查保存。
             2、当员工点击该布料用完时:计算理论长度<理论长度-差异系数时 ，提示实际长度小于理论长度，请检查数据是否正确，点击“是” 则进行保存，点击“否”则返回之前的页面进行修正数据。
             3、当员工删除该卷时：计算理论长度<理论长度-差异系数时 ，提示实际长度小于理论长度，请确认是否用完？点击“是”则删除该卷，并记录对应信息。点击“否”则返回当前扫码界面。
             */
            tailoringTaskPlanPo.setFabricLengthDifference(baseFabricDetailPo.getFabricLengthDifference());

            /**
             * 判断如果剩余需裁剪数量不大于0并且
             * 当输入拉布次数并计算的出的拉布件数>最大允许裁剪数量（最大允许裁剪数量=[Roundup((本次裁剪数量+本次换片数量）/版型分组件数)+系数]*版型分组件数）时，
             * 提示用户裁剪数量大于最大允许裁剪数量，请检查拉布次数是否正确，并返回到扫码输入的界面，当前数据不进行保存，
             * 员工修正数据后点击保存再进行检查并保存
             * 拉布件数<=最大允许裁剪数量（最大允许裁剪数量=[Roundup((本次裁剪数量+本次换片数量）/件数)+系数]*版型分组数），
             */

            double maxQuantity = (Math.round((tailoringTaskPlanPo.getChangePiecesQuantity() + tailoringTaskPlanPo.getQuantity()) / tailoringTaskPlanPo.getTypeQuantity()) + maxAllowTailoringCoefficient) * tailoringTaskPlanPo.getTypeQuantity();
            tailoringTaskPlanPo.setMaxQuantity((int) maxQuantity);
            tailoringTaskPlanDao.save(tailoringTaskPlanPo);
        }
        if (tailoringPlans.size() > 0) {
            List planIds = tailoringPlans.stream().map(TailoringTaskPlanDto::getId).distinct().collect(Collectors.toList());
            tailoringPlanDao.updateStatus(planIds, StatusEnum.TAILORING_TASK_STATUS_START.getCode().toString());
        }

        return po;
    }

    /**
     * 拉布
     * 点击“保存”按钮后.
     * 1、分出不同版型分组。1，2，3
     * 2、计算每个版型的拉布件数。拉布件数=拉布次数*卷数*件数. 1：1000
     * 计算此次拉布件数。
     * 拉布件数=拉布次数*卷数*件数.
     * 拉布次数：屏幕输入的拉布次数。
     * 卷数：选中的行项目数。
     * 件数：裁剪界面输入的件数。比如1版型分组件数3，2版型分组件数4，3版型分组件数5。
     *
     * 3、按顺序计算裁剪计划本次拉布件数。
     * 版型分组1：
     * A：本次裁剪数量300 本次拉布件数=（ 本次裁剪数量 + 本次换片数量 ） 300.
     * B: 本次裁剪数量200本次拉布件数=（ 本次裁剪数量 + 本次换片数量 ）.
     * C：本次裁剪数量800本次拉布件数=（ 本次裁剪数量 + 本次换片数量 ） 500
     *
     * 4、每个版型下，按顺序计算工单剩余需裁剪数（目前逻辑：剩下的拉布件数-（本次裁剪数量+本次换片数量））
     * A: 0 B: 0 C:300
     * 5、计算“实际长度”（实际长度=层高*长度）。层高=拉布次数*卷数。
     *
     * 6、计算“理论长度”。上一次的理论长度-实际长度。
     *
     * 判断剩余需裁剪数量是否大于0，如果剩余需裁剪数量大于0，弹出对话框“布料是否拉完？”。
     * 点击“是”，布料剩余数量直接变为0.本扫码界面全部清空，继续扫码,输入拉布次数、长度。(重新上布)。
     * 点击“否”，扫码界面的拉布次数和长度清空，理论长度需重新计算，其它字段保留，继续输入拉布次数、长度。点击“保存”，重新计算“剩余需裁剪数量。
     * 判断如果剩余需裁剪数量不大于0并且拉布件数<=最大允许裁剪数量（最大允许裁剪数量=[Roundup((本次裁剪数量+本次换片数量）/件数)+系数]*版型分组数），
     * 如果不成立：弹出错误提示框，并返回扫描界面输入本次正确的拉布次数和长度。
     *
     * 如果条件成立，弹出对话框：“是否继续拉布”。
     * 点击“是”，返回扫描界面，继续输入拉布次数和长度
     * 点击“否”，弹出对话框“布料是否用完？“。点击“是”，剩余数量直接变为0.跳转到“未完成输入界面”。
     * 点击“否”，记录布头信息，跳转到“未完成输入界面”。
     *
     * 可参照“系统流程图”。
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

        //布匹列表
        List<TailoringFabricInsertDto> fabrics = tailoringSpreadingDto.getFabrics();

        //检查布匹号不一致
        TailoringSpreadingResultVo resultVo = checkClothNumbers(taskId, fabrics);
        if (resultVo != null) {
            return resultVo;
        }
        //检查任务是否提交
        resultVo = checkTaskSubmit(taskId);
        if (resultVo != null) {
            return resultVo;
        }

        //上次裁剪列表
        List<TailoringTaskPlanDto> tailoringPlans = tailoringSpreadingDto.getTailoringPlans();

        //剩余未完成完成,将已经完成的裁剪计划丢掉，只处理需要裁剪的裁剪计划
        List<TailoringTaskPlanDto> surplusPlans = new ArrayList<>();

        //布头剩余
        Map<Long, Integer> leftMap = new HashMap<>();
        for (TailoringTaskPlanDto dto : tailoringPlans) {
            Integer left = tailoringTaskPlanRecordDao.findByTaskIdEqualsAndProductCodeEqualsMinLeft(taskId, dto.getProductCode());
            if (left == null || left > 0) {
                surplusPlans.add(dto);
                leftMap.put(dto.getId(), left);
            }
        }


        //检查计划完成
        resultVo = checkPlanCompletion(surplusPlans);
        if (resultVo != null) {
            return resultVo;
        }

        //裁剪数量大于最大允许裁剪数量
        resultVo = checkIsTooBig(taskId, spreadingCount, reelCount, surplusPlans);
        if (resultVo != null) {
            return resultVo;
        }

        //上次拉布id
        Long maxSpreadingId = tailoringSpreadingDao.findMaxIdByTaskId(taskId);

        //上次布料列表
        List<TailoringFabricRecordPo> lastFabrics = getLastFabrics(taskId, maxSpreadingId);

        //拉布布料长度超出理论长度太多
        resultVo = checkLengthExceeded(maxSpreadingId, spreadingLength, surplusPlans, lastFabrics, fabrics);
        if (resultVo != null) {
            return resultVo;
        }

        //保存拉布信息
        return spreadingSave(taskId, floor, quantity, spreadingCount, reelCount, planCount, spreadingLength,
                maxSpreadingId, lastFabrics, fabrics, surplusPlans);
    }
    /**
     * 布匹号不一致
     * @param taskId
     * @param fabrics
     * @return
     */
    private TailoringSpreadingResultVo checkClothNumbers(Long taskId,List<TailoringFabricInsertDto> fabrics) {
        //更新任务状态
        TailoringTaskPo tailoringTaskPo = tailoringTaskDao.getOne(taskId);
        TailoringFabricInsertDto tmp = fabrics.get(0);
        if("1".equals(tailoringTaskPo.getIsControlFabric())) {
            for (TailoringFabricInsertDto w : fabrics) {
                if (!tmp.getLotNumber().equals(w.getLotNumber())) {
                    TailoringSpreadingResultVo tailoringSpreadingResultVo = new TailoringSpreadingResultVo();

                    //1 剩余数据量>0 ，2 剩余数据量<0 ，3 不成立,4任务已经提交,5 检查实际长度>理论长度+差异系数
                    tailoringSpreadingResultVo.setStatusEnum(StatusEnum.SPREADING_STATUS_DIFFERENT_CLOTH_NUMBERS);
                    return tailoringSpreadingResultVo;
                }
            }
        }
        return null;
    }


    /**
     * 检查任务已经提交
     * @param taskId
     * @return
     */
    private TailoringSpreadingResultVo checkTaskSubmit(Long taskId){


        //更新任务状态
        TailoringTaskPo tailoringTaskPo = tailoringTaskDao.getOne(taskId);

        //任务没有提交不可以拉布
        if (StatusEnum.TAILORING_TASK_STATUS_START.getCode().toString().equals(tailoringTaskPo.getStatus()) || StatusEnum.TAILORING_TASK_STATUS_DEFAULT.getCode().toString().equals(tailoringTaskPo.getStatus())) {
            return null;
        } else {
            TailoringSpreadingResultVo tailoringSpreadingResultVo = new TailoringSpreadingResultVo();

            //剩余数据量>0 返回1，<0 返回2，不成立返回3,任务已经提交4
            tailoringSpreadingResultVo.setStatusEnum(StatusEnum.SPREADING_STATUS_TASK_SUBMITTED);
            return tailoringSpreadingResultVo;
        }
    }


    /**
     * 检查计划完成
     * @param surplusPlans
     * @return
     */
    private TailoringSpreadingResultVo checkPlanCompletion(List<TailoringTaskPlanDto> surplusPlans){//上次裁剪列表

        if(surplusPlans.size()==0) {
            TailoringSpreadingResultVo tailoringSpreadingResultVo = new TailoringSpreadingResultVo();

            //剩余数据量>0 返回1，<0 返回2，不成立返回3,任务已经提交4,计划完成6
            tailoringSpreadingResultVo.setStatusEnum(StatusEnum.SPREADING_STATUS_PLAN_COMPLETION);
            return tailoringSpreadingResultVo;
        }else{
            return null;
        }

    }

    /**
     * 裁剪数量大于最大允许裁剪数量
     * @param taskId
     * @param spreadingCount
     * @param reelCount
     * @param surplusPlans
     * @return
     */
    private TailoringSpreadingResultVo checkIsTooBig(Long taskId,Integer spreadingCount ,Integer reelCount,List<TailoringTaskPlanDto> surplusPlans) {//上次裁剪列表

        //最大允许拉布数量
        Map<String, List<TailoringTaskPlanDto>> tailoringPlansMap = surplusPlans.stream().collect(Collectors.groupingBy(TailoringTaskPlanDto::getTypeGroup));
        for (String typeGroup : tailoringPlansMap.keySet()) {
            List<TailoringTaskPlanDto> planDtos = tailoringPlansMap.get(typeGroup);

            //拉布件数=拉布次数*卷数*版型件数.
            int spreadingQuantity = spreadingCount * reelCount * planDtos.get(0).getTypeQuantity();
            //同版型最大拉布件数和
            int sumMaxSpreadingQuantity = 0;

            //同版型已经拉布件数和
            int spreadingQuantityTotal = 0;

            for (TailoringTaskPlanDto dto : planDtos) {
                Long planId = dto.getId();
                Integer maxSpreadingQuantity = tailoringTaskPlanDao.findMinMaxQuantityByTaskId(taskId, planId);
                sumMaxSpreadingQuantity += maxSpreadingQuantity;
                Integer sumSpreadingQuantity = tailoringTaskPlanRecordDao.findByTaskIdEqualsAndProductCodeEqualsSumQuantity(taskId, planId);
                sumSpreadingQuantity = sumSpreadingQuantity==null ? 0:sumSpreadingQuantity;
                spreadingQuantityTotal += sumSpreadingQuantity;
            }
            /**
             *  判断如果剩余需裁剪数量不大于0并且
             *  当输入拉布次数并计算的出的拉布件数>最大允许裁剪数量（最大允许裁剪数量=[Roundup((本次裁剪数量+本次换片数量）/版型分组件数)+系数]*版型分组件数）时，
             *  提示用户裁剪数量大于最大允许裁剪数量，请检查拉布次数是否正确，并返回到扫码输入的界面，当前数据不进行保存，
             *  员工修正数据后点击保存再进行检查并保存
             *  拉布件数<=最大允许裁剪数量（最大允许裁剪数量=[Roundup((本次裁剪数量+本次换片数量）/件数)+系数]*版型分组数），
             */
            if ( spreadingQuantity + spreadingQuantityTotal > sumMaxSpreadingQuantity) {
                TailoringSpreadingResultVo tailoringSpreadingResultVo = new TailoringSpreadingResultVo();

                //1 剩余数据量>0 ，2 剩余数据量<0 ，3 不成立,4任务已经提交,5检查实际长度>理论长度+差异系数
                tailoringSpreadingResultVo.setStatusEnum(StatusEnum.SPREADING_STATUS_TAILORING_IS_TOO_BIG);
                return tailoringSpreadingResultVo;
            }
        }
        return null;
    }


    /**
     * 拉布长度超出理论长度太多
     * @param maxSpreadingId
     * @param spreadingLength
     * @param surplusPlans
     * @param lastFabrics
     * @param fabrics
     * @return
     */
    private TailoringSpreadingResultVo checkLengthExceeded(Long maxSpreadingId,int spreadingLength ,List<TailoringTaskPlanDto> surplusPlans,List<TailoringFabricRecordPo> lastFabrics,List<TailoringFabricInsertDto> fabrics) {
        // 差异系数
        Integer minFabricLengthDifference =Integer.MAX_VALUE;
        //最小理论长度
        Integer minTheoryLength = Integer.MAX_VALUE;
        if (maxSpreadingId == null) {
            for (TailoringFabricInsertDto w : fabrics) {
                minTheoryLength = (int) Math.min(w.getTheoryLength(), minTheoryLength);
            }
        } else {

            for (TailoringFabricInsertDto w : fabrics) {
                TailoringFabricRecordPo last = null;
                if (lastFabrics.size() != 0) {
                    List<TailoringFabricRecordPo> a = lastFabrics.stream().filter(
                            d -> w.getReelNumber().equals(d.getReelNumber())
                    ).collect(Collectors.toList());

                    if (a.size() > 0) {
                        last = a.get(0);
                    }
                }
                //完成的计划跳过
                if (last != null) {
                    minTheoryLength = Math.min(last.getLeftLength(), minTheoryLength);
                } else {
                    minTheoryLength = (int)Math.min(w.getTheoryLength(), minTheoryLength);
                }

            }
        }
        /**
         * 当输入拉布次数并计算出理论长度时，
         * 计算“实际长度”（实际长度=层高*长度）。层高=拉布次数*卷数。计算“理论长度”。上一次的理论长度-实际长度。
         * 1，检查实际长度>理论长度+差异系数时，提示用户：拉布长度超出理论长度太多，请检查数据是否正确，如果点"是"，则保存对应的记录。如果点“否”则返回到扫码界面输入界面，员工修正数据后点击保存后再进行检查保存。
         * 2、当员工点击该布料用完时:计算理论长度<理论长度-差异系数时 ，提示实际长度小于理论长度，请检查数据是否正确，点击“是” 则进行保存，点击“否”则返回之前的页面进行修正数据。
         * 3、当员工删除该卷时：计算理论长度<理论长度-差异系数时 ，提示实际长度小于理论长度，请确认是否用完？点击“是”则删除该卷，并记录对应信息。点击“否”则返回当前扫码界面。
         */

        /**
         * 情况1
         */
        for (TailoringTaskPlanDto dto : surplusPlans) {
            Long planId = dto.getId();
            Integer difference =tailoringTaskPlanDao.findFabricLengthDifferenceByPlanIdEquals(planId);
            //最小差异系数
            minFabricLengthDifference = Math.min(difference,minFabricLengthDifference);
        }
        // 检查实际长度
//        int actualLength_check = quantity * spreadingCount*reelCount;
        // 理论长度
//        Integer theoryLength_check = minTheoryLength;
        // 差异系数
        if (spreadingLength > minTheoryLength + minFabricLengthDifference) {
            TailoringSpreadingResultVo tailoringSpreadingResultVo = new TailoringSpreadingResultVo();

            //1 剩余数据量>0 ，2 剩余数据量<0 ，3 不成立,4任务已经提交,5 检查实际长度>理论长度+差异系数
            tailoringSpreadingResultVo.setStatusEnum(StatusEnum.SPREADING_STATUS_LENGTH_EXCEEDED);

            return tailoringSpreadingResultVo;
        }
        return null;
    }

    /**
     * 保存拉布信息
     * @param taskId
     * @param floor
     * @param quantity
     * @param spreadingCount
     * @param reelCount
     * @param planCount
     * @param spreadingLength
     * @param maxSpreadingId
     * @param lastFabrics
     * @param fabrics
     * @param surplusPlans
     * @return
     */
    @Transactional
    public TailoringSpreadingResultVo spreadingSave(Long taskId,Integer floor,Integer quantity,Integer spreadingCount,Integer reelCount,Integer planCount,Integer spreadingLength,
                                                     Long maxSpreadingId,List<TailoringFabricRecordPo> lastFabrics , List<TailoringFabricInsertDto> fabrics,List<TailoringTaskPlanDto> surplusPlans){

        //更新任务状态
        TailoringTaskPo tailoringTaskPo = tailoringTaskDao.getOne(taskId);
        tailoringTaskPo.setStatus(StatusEnum.TAILORING_TASK_STATUS_START.getCode().toString());
        tailoringTaskDao.save(tailoringTaskPo);

        /**
         * 保存拉布信息
         */
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

        BaseFabricDetailPo baseFabricDetailPo = baseFabricDetailDao.findFirstByFabricCodeEquals(fabrics.get(0).getFabricCode());
        List<TailoringFabricRecordPo> resultFabrics = new ArrayList<>();
        for (TailoringFabricInsertDto w : fabrics) {

            TailoringFabricRecordPo last = null;
            if (lastFabrics.size() != 0) {
                List<TailoringFabricRecordPo> a = lastFabrics.stream().filter(
                        d -> w.getReelNumber().equals(d.getReelNumber())
                ).collect(Collectors.toList());

                if (a.size() != 0) {
                    last = a.get(0);
                }

            }

            //布料使用记录
            TailoringFabricRecordPo recordPo = new TailoringFabricRecordPo();
            TailoringUtils.copyProperties(recordPo, w);
            recordPo.setId(null);
            recordPo.setSpreadingId(spreadingId);
            recordPo.setTaskId(taskId);
            recordPo.setDueDate(new Date());
            if(baseFabricDetailPo!=null) {
                recordPo.setActualFabricWidth(baseFabricDetailPo.getFabricWidthMeter());
                recordPo.setTheoryFabricWidth(baseFabricDetailPo.getFabricWidthMeter());
            }
            if (last != null) {
                recordPo.setTheoryLength(Double.valueOf(last.getLeftLength()));
                recordPo.setActualLength(last.getActualLength());
            } else {
                recordPo.setTheoryLength(w.getTheoryLength());
                Double actualLength = tailoringFabricRecordDao.getActualLength(w.getReelNumber());
                recordPo.setActualLength(w.getTheoryLength());
                if (actualLength != null) {
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
        List<TailoringTaskPlanRecordPo> tailoringDetailPos = getLastDetai(taskId, maxSpreadingId);

        //返回结果列表
        List<TailoringTaskPlanRecordPo> tailoringDetailPos1 = new ArrayList<>();


        Map<String, List<TailoringTaskPlanDto>> tailoringPlansMap = surplusPlans.stream().collect(Collectors.groupingBy(TailoringTaskPlanDto::getTypeGroup));
        for (String typeGroup : tailoringPlansMap.keySet()) {
            List<TailoringTaskPlanDto> planDtos = tailoringPlansMap.get(typeGroup);
            //最大可裁剪件数
            //拉布件数=拉布次数*卷数*版型件数.
            int tailoringQuantity = spreadingCount * reelCount * planDtos.get(0).getTypeQuantity();


            for (TailoringTaskPlanDto dto : planDtos) {

                TailoringTaskPlanRecordPo last = null;
                if (tailoringDetailPos.size() != 0) {
                    List<TailoringTaskPlanRecordPo> a = tailoringDetailPos.stream().filter(
                            d -> dto.getProductCode().equals(d.getProductCode())
                    ).collect(Collectors.toList());
                    if (a.size() > 0) {
                    }
                    last = a.get(0);
                }

                //要这个计划要裁剪件数

                Integer spreadingQuantity = (last == null ? dto.getQuantity() + dto.getChangePiecesQuantity() : last.getLeftQuantity());

                Integer leftQuantity = spreadingQuantity - tailoringQuantity;

                TailoringTaskPlanRecordPo detailPo = new TailoringTaskPlanRecordPo();

                TailoringUtils.copyProperties(detailPo, dto);
                detailPo.setId(null);
                detailPo.setTaskId(taskId);
                detailPo.setSpreadingId(spreadingId);
                detailPo.setTailoringPlanId(dto.getId());


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
                tailoringTaskPlanRecordDao.save(detailPo);


                //剩余最大可裁剪件数
                tailoringQuantity = Math.abs(Math.min(leftQuantity, 0));

                //输出结果
                tailoringDetailPos1.add(detailPo);

            }
        }
        //拉布计划id
        List planIds = surplusPlans.stream().map(TailoringTaskPlanDto::getId).distinct().sorted().collect(Collectors.toList());
        tailoringPlanDao.updateWorkQuantity(planIds);
        tailoringPlanDao.updateWorkChangePiecesQuantity(planIds);
        tailoringPlanDao.updateStatus(planIds,StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString());

        List<Long> orderIds = tailoringPlanDao.findOrderIdsByids(planIds);

        tailoringOrderDao.updateWorkQuantity(orderIds);

        //拉布结果
        TailoringSpreadingResultVo tailoringSpreadingResultVo = new TailoringSpreadingResultVo();
        tailoringSpreadingResultVo.setFabrics(resultFabrics);
        tailoringSpreadingResultVo.setTailoringDetailPo(tailoringDetailPos1);

        //剩余数据量>0 返回1，<0 返回2，不成立返回3,任务已经提交4
        tailoringSpreadingResultVo.setStatusEnum(StatusEnum.SPREADING_STATUS_PARTIAL_PLAN_COMPLETED);

        tailoringSpreadingResultVo.setSpreadingId(spreadingId);
        for (TailoringTaskPlanRecordPo po : tailoringDetailPos1) {
            if (po.getLeftQuantity() > 0) {
                tailoringSpreadingResultVo.setStatusEnum(StatusEnum.SPREADING_STATUS_SUCCESS);
                break;
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
    private List<TailoringTaskPlanRecordPo> getLastDetai(Long taskId, Long maxSpreadingId) {
        return tailoringTaskPlanRecordDao.findByTaskIdEqualsAndSpreadingIdEquals(taskId, maxSpreadingId);
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

    /**
     * 任务详情
     *
     * @param taskId 任务id
     * @return
     */
    public TailoringTaskVo taskDetails(Long taskId) {
        TailoringTaskVo tailoringTaskVo = new TailoringTaskVo();

        TailoringTaskPo tailoringTaskPo = tailoringTaskDao.getOne(taskId);

        TailoringUtils.copyProperties(tailoringTaskVo, tailoringTaskPo);

        BaseFabricDetailPo baseFabricDetailPo = baseFabricDetailDao.findFirstByFabricCodeEquals(tailoringTaskPo.getFabricCode());
        if (baseFabricDetailPo != null) {
            tailoringTaskVo.setFabricColour(baseFabricDetailPo.getFabricColour());
            tailoringTaskVo.setFabricWidth(baseFabricDetailPo.getFabricWidthMeter());
            tailoringTaskVo.setFabricWeight(baseFabricDetailPo.getFabricWeight());
        }
        //裁剪计划
        List<TailoringTaskPlanPo> tailoringTaskPlanPos = tailoringTaskPlanDao.findByTaskIdEquals(taskId);
        List<TailoringTaskPlanVo> tailoringTaskPlanVos = new ArrayList<>();
        for(TailoringTaskPlanPo po :tailoringTaskPlanPos){
            TailoringTaskPlanVo vo =new TailoringTaskPlanVo();
            TailoringUtils.copyProperties(vo,po);
            vo.setWorkQuantity(tailoringPlanDao.getOne(po.getTailoringPlanId()).getWorkQuantity());
            tailoringTaskPlanVos.add(vo);
        }

        tailoringTaskVo.setPlans(tailoringTaskPlanVos);

        //裁剪计划详情
        List<TailoringTaskPlanRecordPo> tailoringDetails = tailoringTaskPlanRecordDao.findByTaskIdEquals(taskId);
        tailoringTaskVo.setDetails(tailoringDetails);

        //布料使用记录
        List<TailoringFabricRecordPo> tailoringFabricRecords = tailoringFabricRecordDao.findByTaskIdEquals(taskId);
        tailoringTaskVo.setFabricRecords(tailoringFabricRecords);

        //冲销
        TailoringRecoveryRecordPo tailoringRecoveryRecordPo = tailoringRecoveryRecordDao.findByTaskIdEquals(taskId);
        tailoringTaskVo.setRecoveryRecord(tailoringRecoveryRecordPo);

        return tailoringTaskVo;
    }


    /**
     * 没有提交的任务
     *
     * @return
     */
    public List<TailoringTaskPo> notSubmit() {
        return tailoringTaskDao.findByStatusEquals(StatusEnum.TAILORING_TASK_STATUS_START.getCode().toString());
    }

    public Page<TailoringTaskPo> selectByDate(String startTime, String endTime, Pageable pageable) {
        Page<TailoringTaskPo> result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            QTailoringTaskPo qTailoringTaskPo = QTailoringTaskPo.tailoringTaskPo;

            BooleanExpression be = null;
            if (!StringUtils.isEmpty(startTime)) {
                be = qTailoringTaskPo.createTime.between(sdf.parse(startTime), sdf.parse(endTime));
            } else {
                be = qTailoringTaskPo.id.gt(0);
            }

            //分页
            result = tailoringTaskDao.findAll(be, pageable);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

}