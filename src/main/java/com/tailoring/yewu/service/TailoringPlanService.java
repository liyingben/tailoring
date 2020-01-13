package com.tailoring.yewu.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.tailoring.user.model.Group;
import com.tailoring.user.util.SecurityUtil;
import com.tailoring.user.vo.UserPrincipal;
import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.common.ConstantType;
import com.tailoring.yewu.common.ResultType;
import com.tailoring.yewu.common.StatusEnum;
import com.tailoring.yewu.entity.dto.WorkOrderDto;
import com.tailoring.yewu.entity.po.*;
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
public class TailoringPlanService {

    @Autowired
    private TailoringPlanDao tailoringPlanDao;

    @Autowired
    private BaseFabricUsageDao baseFabricUsageDao;

    @Autowired
    private BaseFabricDetailDao baseFabricDetailDao;

    @Autowired
    private TailoringOrderDao tailoringOrderDao;


    /**
     * 根据工单创建裁剪计划
     *
     * @param pos
     * @return
     */
    @Transactional
    public List<TailoringPlanPo> createPlan(List<WorkOrderDto> pos) {

        List<TailoringPlanPo> list = new ArrayList<>();
        for (WorkOrderDto w : pos) {

            TailoringPlanPo po = new TailoringPlanPo();
            po.setDueDate(w.getDueDate());
            po.setBindingQuantity(w.getBindingQuantity().intValue());
            po.setBoxQuantity(w.getBoxQuantity().intValue());
            po.setWorkOrderNo(w.getWorkOrderNo());
            po.setProductCode(w.getProductCode());
            po.setProductLineNo(Integer.valueOf(w.getProductLineNo()));
            po.setOrderQuantity((int) Math.ceil(w.getWorkOrderQuantity()));
            po.setOrderChangePiecesQuantity((int) Math.ceil(w.getWorkOrderQuantity()*0.01));
            po.setQuantity(0);
            po.setMaxQuantity((int) Math.ceil(w.getWorkOrderQuantity()));
            po.setChangePiecesQuantity(0);
            po.setWorkChangePiecesQuantity(0);
            po.setWorkQuantity(0);

            BaseFabricUsagePo baseFabricUsage = baseFabricUsageDao.findByProductCodeEquals(w.getProductCode());
            if (baseFabricUsage != null) {
                po.setTypeNumber(baseFabricUsage.getTypeNumber());
                po.setFabricCode(baseFabricUsage.getFabricCode());
                po.setFabricWidth(baseFabricUsage.getFabricWidth());
                double changeRate = baseFabricUsage.getChangeRate();
                po.setOrderChangePiecesQuantity((int) (changeRate * po.getOrderQuantity()));

            }

            BaseFabricDetailPo baseFabricDetailPo = null;
            if (baseFabricUsage != null && baseFabricUsage.getFabricCode() != null) {
                baseFabricDetailPo = baseFabricDetailDao.findFirstByFabricCodeEquals(po.getFabricCode());
                po.setFabricLengthDifference(baseFabricDetailPo.getFabricLengthDifference());
                po.setFabricColour(baseFabricDetailPo.getFabricColour());
            }
            po.setMaxChangePiecesQuantity(po.getChangePiecesQuantity());
            po.setStatus(StatusEnum.TAILORING_PLAN_STATUS_DEFAULT.getCode().toString());//正常

            tailoringPlanDao.save(po);

            list.add(po);
        }
        return list;
    }
    @Transactional
    public ActionResult save(List<TailoringPlanPo> pos) {

        //分组：工单+产品编号+产品行号+主辅补
        Map<String,List<TailoringPlanPo>> tailoringPlansMap =new HashMap<>();
        for(TailoringPlanPo po:pos){
            String key = po.getWorkOrderNo()+po.getProductCode()+po.getProductLineNo()+po.getMainSupplement();
            if(StringUtils.isEmpty(po.getMainSupplement())){
                continue;
            }
            if(!tailoringPlansMap.containsKey(key)){
                tailoringPlansMap.put(key,new ArrayList<>());
            }
            tailoringPlansMap.get(key).add(po);
        }

        for (String key : tailoringPlansMap.keySet()) {

            List<TailoringPlanPo> list = tailoringPlansMap.get(key);
            ResultType resultType = checkPlanError(list);
            if (resultType.getCode().intValue() != ResultType.OK.getCode().intValue()) {
                return new ActionResult(resultType, "");
            }
        }
        for(TailoringPlanPo w:pos){

            //选择组别和主辅补才可以开始裁剪
            if (w.getMainSupplement() != null && w.getGroup() != null) {
                w.setStatus(StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString());
            }
            if (w.getMainSupplement() != null) {
                TailoringOrderPo tailoringOrder = tailoringOrderDao.findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoEqualsAndMainSupplementEquals(w.getWorkOrderNo(), w.getProductCode(), w.getProductLineNo(), w.getMainSupplement());
                if (tailoringOrder == null) {
                    tailoringOrder = createTailoringOrder(w);
                }
                w.setOrderId(tailoringOrder.getId());
            }

            tailoringPlanDao.save(w);

            if (StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString().equals(w.getStatus())) {
                try {
                    updatePlanStatus(w.getWorkOrderNo(), w.getProductCode(),w.getProductLineNo(),w.getMainSupplement());
                    updateOrderStatus(w.getWorkOrderNo(), w.getProductCode(),w.getProductLineNo(),w.getMainSupplement());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        return new ActionResult();
    }

    /**
     * 检查保存计划问题
     *
     * @param pos
     * @return
     */
    public ResultType checkPlanError(List<TailoringPlanPo> pos) {
        TailoringPlanPo po = pos.get(0);

        for (TailoringPlanPo planPo : pos) {
            TailoringPlanPo planPo1;
            if(planPo.getId()!=null) {
                 planPo1 = tailoringPlanDao.getOne(planPo.getId());

            } else
            {
                 planPo1=planPo;
            }
            if (!StatusEnum.TAILORING_PLAN_STATUS_DEFAULT.getCode().toString().equals(planPo1.getStatus()) && !StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString().equals(planPo1.getStatus())) {
                //计划不可编辑
                return ResultType.DATA_PLAN_EDIT_STATUS_ERROR;
            }
        }
        //审核计划汇总
        List<Long> planIds = pos.stream().map(TailoringPlanPo::getId).collect(Collectors.toList());

        List<TailoringPlanPo> list = tailoringPlanDao.findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoAndMainSupplementEquals(po.getWorkOrderNo(), po.getProductCode(),po.getProductLineNo(), po.getMainSupplement());

        double totalQuantity = 0d;
        double totalChangePiecesQuantity = 0d;
        for (TailoringPlanPo planPo : list) {
            if (!planIds.contains(planPo.getId())) {
                if ( planPo.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_DEFAULT.getCode().toString()) ||
                        planPo.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString()) ||
                        planPo.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString())) {
                    totalQuantity += planPo.getQuantity();
                    totalChangePiecesQuantity += planPo.getChangePiecesQuantity();
                } else if (!planPo.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_DELETE.getCode().toString())) {
                    totalQuantity += planPo.getWorkQuantity();
                    totalChangePiecesQuantity += planPo.getWorkChangePiecesQuantity();
                }
            }
        }
        for (int i = 0; i < pos.size(); i++) {
            totalQuantity += pos.get(i).getQuantity();
            totalChangePiecesQuantity += pos.get(i).getChangePiecesQuantity();
        }
        double deffQuantity = po.getOrderQuantity() - totalQuantity;
        double deffChangePiecesQuantity = po.getOrderChangePiecesQuantity() - totalChangePiecesQuantity;

        if (deffQuantity < 0) {//超出计划数量
            return ResultType.DATA_PLAN_QUATITY_OUT_ORDER_VALUE;
        } else if (deffChangePiecesQuantity < 0) {//超出换片数量
            return ResultType.DATA_PLAN_CHANGE_PIECES_QUANTITY_OUT_ORDER_VALUE;
        } else {
            return ResultType.OK;//检验通过
        }

    }

    /**
     * 得到编辑的裁剪计划最大可以设置的裁剪数量，
     * 剩余计划的数量 = 工单的数量 - 作业计划数量 - 计划的数量
     * 最大可以设置计划数量 = 当前计划数量 + 剩余计划的数量
     * @param pos
     * @return
     */
    public double findMaxQuantity(List<TailoringPlanPo> pos) {
        pos = pos.stream().filter(
                d -> d.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString())||d.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_DEFAULT.getCode().toString())
        ).collect(Collectors.toList());
        TailoringPlanPo po = pos.get(0);

        //审核计划汇总
        List<Long> planIds = pos.stream().map(TailoringPlanPo::getId).collect(Collectors.toList());

        List<TailoringPlanPo> list = tailoringPlanDao.findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoAndMainSupplementEquals(po.getWorkOrderNo(), po.getProductCode(),po.getProductLineNo(),po.getMainSupplement());

        double total = 0d;
        for (TailoringPlanPo planPo : list) {
            if (!planIds.contains(planPo.getId())) {
                if (planPo.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString()) ||
                        planPo.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_DEFAULT.getCode().toString()) ||
                        planPo.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString())) {
                    total += planPo.getQuantity();
                } else {
                    total += planPo.getWorkQuantity();
                }
            }
        }
        for (int i = 1; i < pos.size(); i++) {
            total += pos.get(i).getQuantity();
        }
        double maxQuantity = po.getOrderQuantity() - total;

        return Math.max(maxQuantity, 0);
    }

    /**
     *  最大可设置的换片数量
     * @param pos
     * @return
     */
    public double findMaxChangePiecesQuantity(List<TailoringPlanPo> pos) {

        pos = pos.stream().filter(
                d -> d.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString())||d.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_DEFAULT.getCode().toString())
        ).collect(Collectors.toList());
        TailoringPlanPo po = pos.get(0);

        //审核计划汇总
        List<Long> planIds = pos.stream().map(TailoringPlanPo::getId).collect(Collectors.toList());

        List<TailoringPlanPo> list = tailoringPlanDao.findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoAndMainSupplementEquals(po.getWorkOrderNo(), po.getProductCode(),po.getProductLineNo(),po.getMainSupplement());


        double total = 0d;
        for (TailoringPlanPo planPo : list) {
            if (!planIds.contains(planPo.getId())) {
                if (planPo.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_DEFAULT.getCode().toString()) ||
                        planPo.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString()) ||
                        planPo.getStatus().equals(StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString())) {
                    total += planPo.getChangePiecesQuantity();
                } else {
                    total += planPo.getWorkChangePiecesQuantity();
                }
            }
        }
        for (int i = 1; i < pos.size(); i++) {
            total += pos.get(i).getChangePiecesQuantity();
        }
        double maxQuantity = po.getOrderChangePiecesQuantity() - total;

        return Math.max(maxQuantity, 0);
    }

    /**
     *  最大可设置的换片数量
     * @param pos
     * @return
     */
    public double simpleMaxChangePiecesQuantity(List<TailoringPlanPo> pos) {

        TailoringPlanPo po = pos.get(0);
        double changeRate=0.01;
        BaseFabricUsagePo baseFabricUsage = baseFabricUsageDao.findByProductCodeEquals(po.getProductCode());
        if (baseFabricUsage != null) {
            changeRate = baseFabricUsage.getChangeRate();
        }

        double maxQuantity = Math.round((po.getQuantity()==null ? 0:po.getQuantity())*changeRate);

        return Math.max(maxQuantity, 0);
    }


    /**
     * 更新工单的状态
     *
     * @param workOrderNo 工单号
     * @param productCode 产品号码
     */
    void updateOrderStatus(String workOrderNo, String productCode,Integer productLineNo,String mainSupplement) {

        if(StringUtils.isEmpty(mainSupplement)){
            return;
        }
        int quantitySum = tailoringPlanDao.findQuantitySumByorder(workOrderNo, productCode,productLineNo,mainSupplement);

        TailoringOrderPo po = tailoringOrderDao.findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoEqualsAndMainSupplementEquals(workOrderNo, productCode,productLineNo,mainSupplement);
        if (po == null) {
            return;
        }

        po.setQuantity(quantitySum + 0.0);
        if (po.getWorkQuantity() > po.getWorkOrderQuantity()) {
            po.setStatus(StatusEnum.TAILORING_ORDER_STATUS_OVER.getCode().toString());
        } else {
            po.setStatus(StatusEnum.TAILORING_ORDER_STATUS_START.getCode().toString());
        }

        tailoringOrderDao.save(po);
    }

    /**
     * 更新裁剪计划的状态
     *
     * @param workOrderNo
     * @param productCode
     */
    void updatePlanStatus(String workOrderNo, String productCode,Integer productLineNo,String mainSupplement) {
        if(StringUtils.isEmpty(mainSupplement)){
            return;
        }
       List<TailoringPlanPo> planPos = tailoringPlanDao.findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoAndMainSupplementEquals(workOrderNo, productCode,productLineNo, mainSupplement);
        //订单裁剪计划的裁剪数量求和
        int sumQuantity = tailoringPlanDao.findQuantitySumByorder(workOrderNo, productCode,productLineNo,mainSupplement);
        //订单裁剪计划的换片率求和
        int sumChangePiecesQuantity = tailoringPlanDao.findChangePiecesQuantitySumByorder(workOrderNo, productCode,productLineNo,mainSupplement);

        planPos.forEach(p -> {
            //最大可裁剪数量
            p.setMaxQuantity((int) (p.getOrderQuantity() - sumQuantity + p.getQuantity()));
            //最大换片率
            p.setMaxChangePiecesQuantity(p.getOrderChangePiecesQuantity() - sumChangePiecesQuantity+ p.getChangePiecesQuantity() );
            tailoringPlanDao.save(p);
        });
    }

    /**
     * 更新裁剪计划状态
     *
     * @param id
     * @param status
     * @return
     */
    public long updatePlanStatus(Long id, String status) {
        TailoringPlanPo po = tailoringPlanDao.getOne(id);
        if (StatusEnum.TAILORING_DETAIL_STATUS_DEFAULT.getCode().toString().equals(po.getStatus())) {
            po.setStatus(status);
            tailoringPlanDao.save(po);
        }
        return po.getId();
    }

    /**
     * 更新裁剪计划状态
     *
     * @param id
     * @return
     */
    public long delete(Long id) {
        TailoringPlanPo po = tailoringPlanDao.getOne(id);
        if (StatusEnum.TAILORING_PLAN_STATUS_DEFAULT.getCode().toString().equals(po.getStatus()) || StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString().equals(po.getStatus())) {
            po.setStatus(StatusEnum.TAILORING_PLAN_STATUS_DELETE.getCode().toString());
            tailoringPlanDao.save(po);
        }
        return po.getId();
    }


    /**
     * 创建裁剪订单
     *
     * @param tailoringPlanPo
     * @return
     */
    public TailoringOrderPo createTailoringOrder(TailoringPlanPo tailoringPlanPo) {
        TailoringOrderPo po = new TailoringOrderPo();
        po.setWorkOrderNo(tailoringPlanPo.getWorkOrderNo());
        po.setProductCode(tailoringPlanPo.getProductCode());
        po.setProductLineNo(tailoringPlanPo.getProductLineNo());
        po.setMainSupplement(tailoringPlanPo.getMainSupplement());
        po.setWorkOrderQuantity(tailoringPlanPo.getOrderQuantity()+0.0);
        po.setTypeNumber(tailoringPlanPo.getTypeNumber());
        po.setTypeQuantity(tailoringPlanPo.getTypeQuantity());
        po.setFabricColour(tailoringPlanPo.getFabricColour());
        po.setFabricCode(tailoringPlanPo.getFabricCode());
        po.setFabricWidth(tailoringPlanPo.getFabricWidth());
        po.setChangePiecesQuantity(tailoringPlanPo.getOrderChangePiecesQuantity());
        po.setBindingQuantity(tailoringPlanPo.getBindingQuantity());
        po.setBoxQuantity(tailoringPlanPo.getBoxQuantity());
        po.setDueDate(tailoringPlanPo.getDueDate());
        po.setWorkQuantity(0.0);
        tailoringOrderDao.save(po);
        return po;
    }
    public Page<TailoringPlanPo> select(String startTime, String endTime, String workOrderNo, String productCode, String[] status, String group, Pageable pageable) {
        Page<TailoringPlanPo> result = null;
        try {

            QTailoringPlanPo qTailoringPlanPo = QTailoringPlanPo.tailoringPlanPo;

            BooleanExpression be;
            if (!StringUtils.isEmpty(startTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                be = qTailoringPlanPo.dueDate.between(sdf.parse(startTime), sdf.parse(endTime));
            } else {
                be = qTailoringPlanPo.id.gt(0);
            }
            if (!StringUtils.isEmpty(workOrderNo)) {
                be = be.and(qTailoringPlanPo.workOrderNo.like(workOrderNo));
            }
            if (!StringUtils.isEmpty(productCode)) {
                be = be.and(qTailoringPlanPo.productCode.like(productCode));
            }
            if (!StringUtils.isEmpty(group)) {
                be = be.and(qTailoringPlanPo.group.like(group));
            }
            if (status != null) {
                String[] statusIn = new String[]{"1", "2", "3", "4", "5", "6"};
                if (status.length == 1) {
                    if ("1".equals(status[0])) {
                        statusIn = new String[]{"1", "2", "3"};
                    } else {
                        statusIn = new String[]{"4", "5", "6"};
                    }
                }
                be = be.and(qTailoringPlanPo.status.in(statusIn));
            }

            //分页
            result = tailoringPlanDao.findAll(be, pageable);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> fabricCodes() {

        UserPrincipal userPrincipal = SecurityUtil.getCurrentUser();
        if (userPrincipal!=null && userPrincipal.getGroups()!=null) {
            List<Long> groupids =userPrincipal.getGroups().stream().map(Group::getId).collect(Collectors.toList());
            List list = tailoringPlanDao.findByStatusEqualsAndGroupIdIn(StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString(),groupids).stream().map(TailoringPlanPo::getFabricCode).distinct().sorted().collect(Collectors.toList());

            if (list.size() == 0) {
                list = tailoringPlanDao.findByStatusEqualsAndGroupIdIn(StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString(),groupids).stream().map(TailoringPlanPo::getFabricCode).distinct().sorted().collect(Collectors.toList());
            }
            return list;
        }else {
            List list = tailoringPlanDao.findByStatusEquals(StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString()).stream().map(TailoringPlanPo::getFabricCode).distinct().sorted().collect(Collectors.toList());

            if (list.size() == 0) {
                list = tailoringPlanDao.findByStatusEquals(StatusEnum.TAILORING_PLAN_STATUS_WAIT.getCode().toString()).stream().map(TailoringPlanPo::getFabricCode).distinct().sorted().collect(Collectors.toList());
            }
            return list;
        }
    }

    /**
     * 根据布料编码查询裁剪计划
     *
     * @param fabricCode
     * @param status
     * @return
     */
    public List<TailoringPlanPo> findByFabricCodeEqualsAndStatus(String fabricCode, String status) {

        QTailoringPlanPo qTailoringPlanPo = QTailoringPlanPo.tailoringPlanPo;

        BooleanExpression be = qTailoringPlanPo.status.eq(status);
        if (!StringUtils.isEmpty(fabricCode) && !ConstantType.FABRIC_CODE_ALL.getDesc().equals(fabricCode)) {
            be = be.and(qTailoringPlanPo.fabricCode.eq(fabricCode));
        }

        UserPrincipal userPrincipal = SecurityUtil.getCurrentUser();
        if (userPrincipal!=null && userPrincipal.getGroups()!=null) {
            be = be.and(qTailoringPlanPo.groupId.in(userPrincipal.getGroups().stream().map(Group::getId)
                    .collect(Collectors.toList())));
        }

        List<TailoringPlanPo> result = new ArrayList<>();
        Iterator integer = tailoringPlanDao.findAll(be).iterator();
        while (integer.hasNext()) {
            result.add((TailoringPlanPo) integer.next());
        }

        return result;
    }

    /**
     * 根据id 查询裁剪计划
     *
     * @param id
     * @return
     */
    public TailoringPlanPo selectById(long id) {
        return tailoringPlanDao.getOne(id);
    }
}