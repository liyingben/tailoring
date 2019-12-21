package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringDetailPo;
import com.tailoring.yewu.entity.po.TailoringTaskPlanPo;
import com.tailoring.yewu.entity.po.TailoringTaskPo;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface TailoringTaskPlanDao extends JpaRepository<TailoringTaskPlanPo, Long> {
    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime    结束时间
     * @return
     */
    List<TailoringTaskPlanPo> findByCreateTimeBetween(Date startTime, Date endTime);

    List<TailoringTaskPlanPo> findByTaskIdEquals(Long taskId);


}
