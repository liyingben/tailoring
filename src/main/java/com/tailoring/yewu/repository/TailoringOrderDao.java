package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringOrderPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * User Dao
 * </p>
 *
 * @package: com.tailoring.yewu.repository
 * @description: User Dao
 * @author: ben
 * @date: Created in 2018/11/7 14:07
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Repository
public interface TailoringOrderDao extends JpaRepository<TailoringOrderPo, Long> {


    TailoringOrderPo findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoEqualsAndMainSupplementEquals(String workOrderNO, String productCode,Integer productLineNo,String mainSupplement);

    /**
     * 更新裁剪订单作业数量
     *
     * @param orderIds
     */
    @Query(value = "update tailoring_order SET tailoring_order.work_quantity = b.work_quantity FROM tailoring_order , (SELECT order_id,sum(work_quantity) as work_quantity from tailoring_plan WHERE order_id in (:orderIds)  GROUP BY order_id) b WHERE  tailoring_order.id = b.order_id", nativeQuery = true)
    @Modifying
    @Transactional
    void updateWorkQuantity(@Param("orderIds") List<Long> orderIds);

    /**
     * 更新裁剪订单状态
     * @param orderIds
     */
    @Query(value = "update tailoring_order SET status=:status WHERE  id in (:orderIds)", nativeQuery = true)
    @Modifying
    @Transactional
    void updateStatus(@Param("orderIds") List<Long> orderIds, String status);

}
