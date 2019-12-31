package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringExamineFabricRecordPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 审核布料汇总 Dao
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
public interface TailoringExamineFabricRecordDao extends JpaRepository<TailoringExamineFabricRecordPo, Long>, QuerydslPredicateExecutor<TailoringExamineFabricRecordPo> {

    List<TailoringExamineFabricRecordPo> findByTailoringNoEquals(String tailoringNo);


    /**
     * 插入审核计划布料使用记录
     *
     * @param taskIds
     * @param tailoringNo
     */
    @Query(value = "INSERT INTO tailoring_examine_fabric_record" +
            "(create_time" +
            ",update_time" +
            ",actual_fabric_width" +
            ",actual_length" +
            ",change_pieces_quantity" +
            ",fabric_code" +
            ",floor" +
            ",left_length" +
            ",lot_number" +
            ",quantity" +
            ",reel_number" +
            ",spreading_count" +
            ",spreading_length" +
            ",sum_length" +
            ",theory_fabric_width" +
            ",theory_length" +
            ",tailoring_no) " +
            "SELECT" +
            "      min(create_time) as create_time" +
            "     ,min(update_time) as update_time" +
            "     ,max(actual_fabric_width) as actual_fabric_width" +
            "     ,max(actual_length) as actual_length" +
            "     ,sum(change_pieces_quantity) as change_pieces_quantity" +
            "     ,min(fabric_code) as fabric_code" +
            "     ,sum(floor) as floor" +
            "     ,min(left_length) as left_length" +
            "     ,min(lot_number) as lot_number" +
            "     ,sum(quantity) as  quantity" +
            "     ,reel_number" +
            "     ,sum(spreading_count) as spreading_count" +
            "     ,sum(spreading_length) as spreading_length" +
            "     ,sum(sum_length) as sum_length" +
            "     ,min(theory_fabric_width) as theory_fabric_width" +
            "     ,min(theory_length) as theory_length" +
            "     ,:tailoringNo as tailoring_no" +
            " FROM tailoring_fabric_record where task_id in (:taskIds) GROUP BY reel_number", nativeQuery = true)
    @Modifying
    @Transactional
    void insertFabricRecord(@Param("taskIds") List<Long> taskIds, @Param("tailoringNo") String tailoringNo);


}
