package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringPlanPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

public interface TailoringPlanDao extends JpaRepository<TailoringPlanPo, Long>, QuerydslPredicateExecutor<TailoringPlanPo> {
    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<TailoringPlanPo> findByCreateTimeBetween(Date startTime, Date endTime);

    @Query(value = "SELECT p FROM TailoringPlanPo p where p.workOrderNo=?1 and p.productCode=?2 and p.productLineNo=?3 and p.mainSupplement=?4 and p.status in ('1','2','3','4','5','6')")
    List<TailoringPlanPo> findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoAndMainSupplementEquals(String workOrderNO, String productCode,Integer productLineNo,String mainSupplement);

    List<TailoringPlanPo> findByStatusEquals(String status);


    @Query(value = "SELECT sum(quantity) FROM tailoring_plan where work_order_no=?1 and product_code=?2 and product_line_no=?3 and main_supplement=?4 and status in ('1','2','3','4','5','6')", nativeQuery = true)
    Integer findQuantitySumByorder(String workOrderNo, String productCode,Integer productLineNo,String mainSupplement);

    @Query(value = "SELECT sum(change_pieces_quantity) FROM tailoring_plan where work_order_no=?1 and product_code=?2 and product_line_no=?3 and main_supplement=?4 and status in ('1','2','3','4','5','6')", nativeQuery = true)
    Integer findChangePiecesQuantitySumByorder(String workOrderNo, String productCode,Integer productLineNo,String mainSupplement);

    /**
     * 更新计划的裁剪作业数量和最大可裁剪数量
     *
     * @param planIds
     */
    @Query(value = "update tailoring_plan SET tailoring_plan.work_quantity = b.quantity,tailoring_plan.max_quantity=(tailoring_plan.quantity - b.quantity) FROM tailoring_plan , (SELECT tailoring_plan_id,sum(spreading_quantity) as quantity from tailoring_detail WHERE tailoring_plan_id in (:planIds)  GROUP BY tailoring_plan_id) b WHERE  tailoring_plan.id = b.tailoring_plan_id", nativeQuery = true)
    @Modifying
    @Transactional
    void updateWorkQuantity(@Param("planIds") List<Long> planIds);

    /**
     * 更新计划的裁剪作业数量和最大可裁剪数量
     *
     * @param planIds
     */
    @Query(value = "update tailoring_plan SET tailoring_plan.work_change_pieces_quantity = b.change_pieces_quantity,tailoring_plan.max_change_pieces_quantity=(tailoring_plan.change_pieces_quantity - b.change_pieces_quantity) FROM tailoring_plan , (SELECT tailoring_plan_id,sum(change_pieces_quantity) as change_pieces_quantity from tailoring_task_plan WHERE tailoring_plan_id in (:planIds)  GROUP BY tailoring_plan_id) b WHERE  tailoring_plan.id = b.tailoring_plan_id", nativeQuery = true)
    @Modifying
    @Transactional
    void updateWorkChangePiecesQuantity(@Param("planIds") List<Long> planIds);

    /**
     * 更新计划的裁剪作业数量和最大可裁剪数量
     *
     * @param planIds
     */
    @Query(value = "update tailoring_plan SET status=:status WHERE  id in (:planIds)", nativeQuery = true)
    @Modifying
    @Transactional
    void updateStatus(@Param("planIds") List<Long> planIds, String status);


    /**
     * 更新计划的裁剪作业数量和最大可裁剪数量
     *
     * @param taskIds
     */
    @Query(value = "update tailoring_plan SET status=:status WHERE  id in (select tailoring_plan_id from tailoring_task_plan where task_id in (:taskIds) group by tailoring_plan_id)", nativeQuery = true)
    @Modifying
    @Transactional
    void updateStatusByTaskIds(@Param("taskIds") List<Long> taskIds,@Param("status")  String status);



    @Query(value = "SELECT order_id FROM tailoring_plan where id in (:planIds) GROUP BY order_id", nativeQuery = true)
    List<Long> findOrderIdsByids(@Param("planIds") List<Long> planIds);


}
