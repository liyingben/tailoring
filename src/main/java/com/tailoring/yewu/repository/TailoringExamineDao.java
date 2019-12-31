package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringExaminePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

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
public interface TailoringExamineDao extends JpaRepository<TailoringExaminePo, Long>, QuerydslPredicateExecutor<TailoringExaminePo> {
    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<TailoringExaminePo> findByCreateTimeBetween(Date startTime, Date endTime);

}
