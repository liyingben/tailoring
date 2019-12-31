package com.tailoring.yewu.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tailoring.yewu.entity.po.QBaseFabricUsagePo;
import com.tailoring.yewu.entity.po.QTailoringOrderPo;
import com.tailoring.yewu.entity.po.QWorkOrderPo;
import com.tailoring.yewu.entity.vo.WorkOrderVo;
import com.tailoring.yewu.repository.WorkOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class WorkOrderService {


    @Autowired
    private WorkOrderDao workOrderDao;


    @Autowired
    @PersistenceContext
    EntityManager em;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }



    public Page<WorkOrderVo> select(String startTime, String endTime,String fabricCode,String dept, Pageable pageable) {

        //订单
        QWorkOrderPo workOrderPo = QWorkOrderPo.workOrderPo;
        //裁剪订单
        QTailoringOrderPo tailoringOrderPo = QTailoringOrderPo.tailoringOrderPo;
        QBaseFabricUsagePo baseFabricUsagePo=QBaseFabricUsagePo.baseFabricUsagePo;
        BooleanExpression be = null;

        if (!StringUtils.isEmpty(startTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                be = workOrderPo.dueDate.between(sdf.parse(startTime), sdf.parse(endTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            be = workOrderPo.id.gt(0);
        }
        if (!StringUtils.isEmpty(fabricCode) ) {
            be = be.and(baseFabricUsagePo.fabricCode.eq(fabricCode));
        }
        if (!StringUtils.isEmpty(dept) ) {
            be = be.and(workOrderPo.dept.eq(dept));
        }


        JPAQuery<WorkOrderVo> jpaQuery = queryFactory.select(
                Projections.bean(
                        WorkOrderVo.class,//返回自定义实体的类型
                        workOrderPo.id,
                        workOrderPo.workOrderNo,
                        workOrderPo.productLineNo,
                        workOrderPo.productCode,
                        workOrderPo.description,
                        workOrderPo.dueDate,
                        workOrderPo.workOrderQuantity,
                        workOrderPo.remainingQuantity,
                        workOrderPo.boxQuantity,
                        workOrderPo.bindingQuantity,
                        baseFabricUsagePo.typeNumber,
                        baseFabricUsagePo.fabricCode,
                        baseFabricUsagePo.fabricWidth,
                        baseFabricUsagePo.changeRate,
                        baseFabricUsagePo.fabricColour,
                        tailoringOrderPo.quantity,
                        tailoringOrderPo.workQuantity
                ))
                .from(workOrderPo)//构建两表笛卡尔集
                .leftJoin(tailoringOrderPo).on(workOrderPo.workOrderNo.eq(tailoringOrderPo.workOrderNo).and(workOrderPo.productCode.eq(tailoringOrderPo.productCode)))
                .leftJoin(baseFabricUsagePo).on(workOrderPo.productCode.eq(baseFabricUsagePo.productCode))
                .where(be)//关联两表
                .orderBy(parase(pageable))//倒序
                .limit(pageable.getPageSize())
                .offset((pageable.getPageNumber())*pageable.getPageSize());
       return new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.fetchCount());

    }
    public OrderSpecifier parase(Pageable pageable){
        //订单
        QWorkOrderPo workOrderPo = QWorkOrderPo.workOrderPo;
        //裁剪订单
//        QTailoringOrderPo tailoringOrderPo = QTailoringOrderPo.tailoringOrderPo;
//        QBaseFabricUsagePo baseFabricUsagePo=QBaseFabricUsagePo.baseFabricUsagePo;

        Sort sort =pageable.getSort();
        if(sort.getOrderFor("dueDate")!=null){
            if(sort.getOrderFor("dueDate").getDirection().isAscending()){
                return workOrderPo.dueDate.asc();
            }else{
                return workOrderPo.dueDate.desc();
            }

        }else if(sort.getOrderFor("workOrderQuantity")!=null){
            if(sort.getOrderFor("workOrderQuantity").getDirection().isAscending()){
                return workOrderPo.workOrderQuantity.asc();
            }else{
                return workOrderPo.workOrderQuantity.desc();
            }
        }else if(sort.getOrderFor("workOrderNo")!=null){
            if(sort.getOrderFor("workOrderNo").getDirection().isAscending()){
                return workOrderPo.workOrderNo.asc();
            }else{
                return workOrderPo.workOrderNo.desc();
            }
        }else{
            return workOrderPo.dueDate.desc();
        }

    }

}