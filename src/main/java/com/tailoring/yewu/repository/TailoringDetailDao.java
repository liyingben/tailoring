package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringDetailPo;
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
public interface TailoringDetailDao extends JpaRepository<TailoringDetailPo, Long> {
    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime    结束时间
     * @return
     */
    List<TailoringDetailPo> findByCreateTimeBetween(Date startTime, Date endTime);


    List<TailoringDetailPo> findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoEquals(String workOrderNo,String productCode,Integer productLineNo);
    TailoringDetailPo findByTailoringPlanIdEquals(Long tailoringPlanId);

    List<TailoringDetailPo> findByStatusEquals(String status);
    List<TailoringDetailPo> findByTaskIdEquals(Long taskId);

}
