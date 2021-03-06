package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringDetailPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
public interface TailoringDetailDao extends JpaRepository<TailoringDetailPo, Long> {
    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<TailoringDetailPo> findByCreateTimeBetween(Date startTime, Date endTime);

    List<TailoringDetailPo> findByTaskIdEquals(Long taskId);

    List<TailoringDetailPo> findByTaskIdIn(List<Long> taskIds);

    List<TailoringDetailPo> findByTaskIdEqualsAndSpreadingIdEquals(Long taskId, Long spreadingId);

    @Query(value = "SELECT min(left_quantity) FROM tailoring_detail where task_id=?1 and product_code=?2", nativeQuery = true)
    Integer findByTaskIdEqualsAndProductCodeEqualsMinLeft(Long taskId, String ProductCode);

    @Query(value = "SELECT min(left_quantity) FROM tailoring_detail where task_id=?1 and tailoring_plan_id=?2", nativeQuery = true)
    Integer findByTaskIdEqualsAndProductCodeEqualsMinLeft(Long taskId, Long planId);

    @Query(value = "SELECT sum(spreading_quantity) FROM tailoring_detail where task_id=?1 and tailoring_plan_id=?2", nativeQuery = true)
    Integer findByTaskIdEqualsAndProductCodeEqualsSumQuantity(Long taskId, Long planId);

}
