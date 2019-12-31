package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringExamineNoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

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
public interface TailoringExamineNoDao extends JpaRepository<TailoringExamineNoPo, Long>, QuerydslPredicateExecutor<TailoringExamineNoPo> {

    TailoringExamineNoPo findFirstByOrderByIdDesc();


}
