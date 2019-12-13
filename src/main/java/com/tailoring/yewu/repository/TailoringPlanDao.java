package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringPlanPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * User Dao
 * </p>
 *
 * @package: com.tailoring.yewu.repository
 * @description: User Dao
 * @author: yangkai.shen
 * @date: Created in 2018/11/7 14:07
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Repository
public interface TailoringPlanDao extends JpaRepository<TailoringPlanPo, Long> , QuerydslPredicateExecutor<TailoringPlanPo> {
    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime    结束时间
     * @return
     */
    List<TailoringPlanPo> findByCreateTimeBetween(Date startTime, Date endTime);
    List<TailoringPlanPo> findByWorkOrderNoEquals(String workOrderNO);

    List<TailoringPlanPo> findByWorkOrderNoEqualsAndProductCodeEquals(String workOrderNO,String productCode);
    List<TailoringPlanPo> findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoEquals(String workOrderNO,String productCode,Integer productLineNo);

    List<TailoringPlanPo> findByStatusEquals(String status);

    @Query(value="SELECT sum(quantity) FROM tailoring_plan where work_order_no=?1 and product_code=?2", nativeQuery=true)
    Integer  findQuantitySumByorder(String workOrderNo, String productCode);

}
