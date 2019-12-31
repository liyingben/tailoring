package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringTaskPo;
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
public interface TailoringTaskDao extends JpaRepository<TailoringTaskPo, Long>, QuerydslPredicateExecutor<TailoringTaskPo> {

    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<TailoringTaskPo> findByCreateTimeBetween(Date startTime, Date endTime);

    List<TailoringTaskPo> findByStatusEquals(String status);

    List<TailoringTaskPo> findByTailoringNoEquals(String tailoringNo);

    /**
     * 更新任务状态,和流水号
     *
     * @param taskIds
     * @param status
     */
    @Modifying
    @Transactional
    @Query(value = "update tailoring_task SET status=:status,tailoring_no=:tailoringNo WHERE  id in (:taskIds)", nativeQuery = true)
    void updateStatus(@Param("taskIds") List<Long> taskIds, @Param("status") String status, @Param("tailoringNo") String tailoringNo);

}
