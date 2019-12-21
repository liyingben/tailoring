package com.tailoring.yewu.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.tailoring.yewu.common.ConstantType;
import com.tailoring.yewu.common.StatusEnum;
import com.tailoring.yewu.entity.dto.WorkOrderDto;
import com.tailoring.yewu.entity.po.*;
import com.tailoring.yewu.repository.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TailoringPlanService {

    @Autowired
    private TailoringPlanDao tailoringPlanDao;
    @Autowired
    private BaseHpwDao baseHpwDao;
    @Autowired
    private BaseFabricUsageDao baseFabricUsageDao;

    @Autowired
    private WorkOrderStatusDao workOrderStatusDao;

    @Autowired
    private TailoringOrderDao tailoringOrderDao;

    @Autowired
    private WorkOrderDao workOrderDao;

    public void save(List<TailoringPlanPo> pos) {
        pos.forEach(w -> {
            //只可以修改未完成订单
            if(StatusEnum.TAILORING_PLAN_STATUS_DEFAULT.getCode().toString().equals(w.getStatus())) {
                double quantityTotal =0;
                try {
                    quantityTotal = tailoringPlanDao.findQuantitySumByorder(w.getWorkOrderNo(), w.getProductCode());
                }catch (Exception e){
                    //没有计划
                }
                WorkOrderPo workOrderPo = workOrderDao.findByWorkOrderNoEqualsAndProductCode(w.getWorkOrderNo(), w.getProductCode());
                if (workOrderPo.getWorkOrderQuantity() >= quantityTotal) {

//                    BaseFabricUsagePo baseFabricUsage = baseFabricUsageDao.findByProductCodeEquals(w.getProductCode());
//                    if (baseFabricUsage != null) {
//                        double changeRate =  Double.parseDouble(baseFabricUsage.getChangeRate());
//                        w.setChangePiecesQuantity((int) (changeRate * w.getQuantity()));
//        //                po.setMaxQuantity(baseFabricUsage.getMaxFloorHeight());
//                    }

                    tailoringPlanDao.save(w);
                }

            }
        });

        pos.forEach(w -> {
            //只可以修改未完成订单
            if(StatusEnum.TAILORING_PLAN_STATUS_DEFAULT.getCode().toString().equals(w.getStatus())) {
                try {
                    updatePlanStatus(w.getWorkOrderNo(), w.getProductCode());
                    updateOrderStatus(w.getWorkOrderNo(), w.getProductCode());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 根据工单创建裁剪计划
     *
     * @param pos
     * @return
     */
    public List<TailoringPlanPo> createPlan(List<WorkOrderDto> pos) {

        List<TailoringPlanPo> list = new ArrayList<>();
        for (WorkOrderDto w : pos) {
            double quantityTotal =0;
            try {
                quantityTotal = tailoringPlanDao.findQuantitySumByorder(w.getWorkOrderNo(), w.getProductCode());
            }catch (Exception e){
                //没有计划
            }

            if (w.getWorkOrderQuantity() <= quantityTotal) {
                continue;
            }

            TailoringPlanPo po = new TailoringPlanPo();
            po.setDueDate(w.getDueDate());
            po.setQuantity((int) Math.ceil(w.getWorkOrderQuantity() - quantityTotal));
            po.setBindingQuantity(w.getBindingQuantity().intValue());
            po.setBoxQuantity(w.getBoxQuantity().intValue());
            po.setWorkOrderNo(w.getWorkOrderNo());
            po.setProductCode(w.getProductCode());
            po.setProductLineNo(Integer.valueOf(w.getProductLineNo()));
            po.setChangePiecesQuantity((int)(po.getQuantity()*0.01));
            po.setMaxQuantity(po.getQuantity());

            po.setWorkChangePiecesQuantity(0);
//            po.setMainSupplement("主");

            BaseFabricUsagePo baseFabricUsage = baseFabricUsageDao.findByProductCodeEquals(w.getProductCode());
            if (baseFabricUsage != null) {
                po.setTypeNumber(baseFabricUsage.getTypeNumber());
                po.setFabricCode(baseFabricUsage.getFabricCode());
                po.setFabricWidth(baseFabricUsage.getFabricWidth());
                double changeRate =  Double.parseDouble(baseFabricUsage.getChangeRate());
                 po.setChangePiecesQuantity((int) (changeRate * po.getQuantity()));
//                po.setMaxQuantity(baseFabricUsage.getMaxFloorHeight());
            }
            po.setMaxChangePiecesQuantity(po.getChangePiecesQuantity());
            BaseHpwPo baseHpw = baseHpwDao.findByProductCodeEquals(w.getProductCode());
            if (baseHpw != null) {
                po.setFabricColour(baseHpw.getColour());
            }

            po.setStatus(StatusEnum.TAILORING_PLAN_STATUS_DEFAULT.getCode().toString());//正常

            tailoringPlanDao.save(po);

            //创建裁剪订单
            createTailoringOrder(w,po);

            updateOrderStatus(w.getWorkOrderNo(),w.getProductCode());
            updatePlanStatus(w.getWorkOrderNo(),w.getProductCode());
            list.add(po);
        }
        return list;
    }

    /**
     * 更新工单的状态
     * @param workOrderNo 工单号
     * @param productCode 产品号码
     */
    void updateOrderStatus(String workOrderNo, String productCode){

        WorkOrderPo workOrderPo = workOrderDao.findByWorkOrderNoEqualsAndProductCode(workOrderNo,productCode);
        int quantitySum = tailoringPlanDao.findQuantitySumByorder(workOrderNo,productCode);

        WorkOrderStatusPo po = workOrderStatusDao.findByWorkOrderNoEqualsAndProductCode(workOrderNo,productCode);
        if(po == null){
            po = new WorkOrderStatusPo();
            po.setWorkOrderNo(workOrderNo);
            po.setProductCode(productCode);
            po.setWorkOrderQuantity(workOrderPo.getWorkOrderQuantity());
        }
        po.setMaxQuantity((int)(workOrderPo.getWorkOrderQuantity()-quantitySum));
        if(po.getMaxQuantity()>0) {
            po.setStatus(StatusEnum.TAILORING_ORDER_STATUS_START.getCode().toString());
        }else {
            po.setStatus(StatusEnum.TAILORING_ORDER_STATUS_OVER.getCode().toString());
        }
        workOrderStatusDao.save(po);
    }

    /**
     * 更新裁剪计划的状态
     * @param workOrderNo
     * @param productCode
     */
    void updatePlanStatus(String workOrderNo, String productCode){
        WorkOrderPo workOrderPo = workOrderDao.findByWorkOrderNoEqualsAndProductCode(workOrderNo,productCode);
        List<TailoringPlanPo> planPos = tailoringPlanDao.findByWorkOrderNoEqualsAndProductCodeEquals(workOrderNo, productCode);
        //订单裁剪计划的裁剪数量求和
        int sumQuantity = tailoringPlanDao.findQuantitySumByorder(workOrderNo,productCode);
        //订单裁剪计划的换片率求和
        int sumChangePiecesQuantity = tailoringPlanDao.findChangePiecesQuantitySumByorder(workOrderNo,productCode);;

        planPos.forEach(p -> {
            //最大可裁剪数量
            p.setMaxQuantity((int) (workOrderPo.getWorkOrderQuantity()- sumQuantity+p.getQuantity()));
            //最大换片率
            p.setMaxChangePiecesQuantity(Double.valueOf(workOrderPo.getWorkOrderQuantity()*0.01+p.getChangePiecesQuantity()-sumChangePiecesQuantity).intValue());
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
        if(StatusEnum.TAILORING_DETAIL_STATUS_DEFAULT.getCode().toString().equals(po.getStatus())){
            po.setStatus(status);
            tailoringPlanDao.save(po);
        }
        return po.getId();
    }



    public long createTailoringOrder(WorkOrderPo workOrderPo,TailoringPlanPo planPo) {
        TailoringOrderPo po = new TailoringOrderPo();
        try {
            BeanUtils.copyProperties(po, workOrderPo);
            BeanUtils.copyProperties(po, planPo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        po.setId(null);
        po.setWorkQuantity(0);

        tailoringOrderDao.save(po);
        return po.getId();
    }
    public Page<TailoringPlanPo> select(String startTime, String endTime, String workOrderNo, String productCode, String[] status, Pageable pageable) {
        Page<TailoringPlanPo> result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {

            QTailoringPlanPo qTailoringPlanPo = QTailoringPlanPo.tailoringPlanPo;

            BooleanExpression be = qTailoringPlanPo.dueDate.between(sdf.parse(startTime), sdf.parse(endTime));
            if (!StringUtils.isEmpty(workOrderNo))
                be = be.and(qTailoringPlanPo.workOrderNo.like(workOrderNo));
            if (!StringUtils.isEmpty(productCode))
                be = be.and(qTailoringPlanPo.productCode.like(productCode));
            if (status != null)
                be = be.and(qTailoringPlanPo.status.in(status));

            //分页
            result = tailoringPlanDao.findAll(be, pageable);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> fabricCodes(String status) {
        List fcs = tailoringPlanDao.findByStatusEquals(status).stream().map(TailoringPlanPo::getFabricCode).distinct().sorted().collect(Collectors.toList());
        if(!fcs.contains(ConstantType.FABRIC_CODE_ALL.getDesc())){
            fcs.add(0,ConstantType.FABRIC_CODE_ALL.getDesc());
        }
        return fcs;
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
        if (!StringUtils.isEmpty(fabricCode)&&!ConstantType.FABRIC_CODE_ALL.getDesc().equals(fabricCode))
            be = be.and(qTailoringPlanPo.fabricCode.eq(fabricCode));

        List<TailoringPlanPo> result = new ArrayList<>();
        //分页
        Iterator integer = tailoringPlanDao.findAll(be).iterator();
        while (integer.hasNext()) {
            result.add((TailoringPlanPo) integer.next());
        }

        return result;
    }
    /**
     * 根据布料编码查询裁剪计划
     *
     * @param fabricCode
     * @param status
     * @return
     */
    public List<TailoringPlanPo> findByFabricCodeEqualsAndStatus(String fabricCode, List<String> status) {
        List<TailoringPlanPo> result = tailoringPlanDao.findByFabricCodeEqualsAndStatusIn(fabricCode, status);
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