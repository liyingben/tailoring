package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringTaskPlanPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
 * @author: ben
 * @date: Created in 2018/11/7 14:07
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Repository
public interface TailoringTaskPlanDao extends JpaRepository<TailoringTaskPlanPo, Long> {
    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<TailoringTaskPlanPo> findByCreateTimeBetween(Date startTime, Date endTime);

    List<TailoringTaskPlanPo> findByTaskIdEquals(Long taskId);

    @Query(value = "SELECT min(max_quantity) FROM tailoring_task_plan where task_id=?1", nativeQuery = true)
    Integer findMinMaxQuantityByTaskId(Long taskId);

    @Query(value = "SELECT fabric_length_difference FROM tailoring_task_plan where tailoring_plan_id=?1", nativeQuery = true)
    Integer findFabricLengthDifferenceByPlanIdEquals(Long planId);
}
