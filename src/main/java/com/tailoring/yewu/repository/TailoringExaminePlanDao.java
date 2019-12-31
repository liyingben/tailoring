package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringExaminePlanPo;
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
 * 审核 Dao
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
public interface TailoringExaminePlanDao extends JpaRepository<TailoringExaminePlanPo, Long>, QuerydslPredicateExecutor<TailoringExaminePlanPo> {
    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<TailoringExaminePlanPo> findByCreateTimeBetween(Date startTime, Date endTime);

    List<TailoringExaminePlanPo> findByTailoringNoEquals(String tailoringNo);


    /**
     * 更新任务状态,和流水号
     *
     * @param taskIds
     */
    @Query(value = "INSERT INTO tailoring_examine_plan (" +
            "tailoring_plan_id," +
            "tailoring_no," +
            "work_order_no, " +
            "type_group, " +
            "product_line_no, " +
            "product_code, " +
            "product_quantity," +
            "fabric_code, " +
            "spreading_count, " +
            "floor, " +
            "quantity, " +
            "binding_quantity," +
            "change_pieces_quantity," +
            " spreading_quantity, " +
            "type_quantity, " +
            "main_supplement," +
            "create_time, " +
            "update_time)" +

            "(SELECT" +
            "    tailoring_plan_id" +
            "     ,:tailoringNo as tailoring_no" +
            "     ,min(work_order_no) as work_order_no" +
            "     ,min(type_group) as type_group" +
            "     ,min(product_line_no) as product_line_no" +
            "     ,min(product_code) as product_code" +
            "     ,sum(spreading_quantity) as product_quantity" +
            "     ,min(fabric_code) as fabric_code" +
            "     ,sum(spreading_count) as spreading_count" +
            "     ,sum(floor) as floor" +
            "     ,sum(quantity) as quantity" +
            "     ,sum(binding_quantity) as binding_quantity" +
            "     ,sum(change_pieces_quantity) as change_pieces_quantity" +
            "     ,sum(spreading_quantity) as spreading_quantity" +
            "     ,sum(type_quantity) as type_quantity" +
            "     ,min(main_supplement) as main_supplement" +
            "     ,min(create_time) as  create_time" +
            "     ,min(update_time) as update_time " +
            "FROM tailoring_detail  where task_id in (:taskIds) GROUP BY tailoring_plan_id)", nativeQuery = true)
    @Modifying
    @Transactional
    void insertPlan(@Param("taskIds") List<Long> taskIds, @Param("tailoringNo") String tailoringNo);


}
