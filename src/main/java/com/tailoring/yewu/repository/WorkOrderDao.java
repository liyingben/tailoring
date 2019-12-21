package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.WorkOrderPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

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
public interface WorkOrderDao extends JpaRepository<WorkOrderPo, Long> {
    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime    结束时间
     * @return
     */
    Page<WorkOrderPo> findByDueDateBetween(Date startTime, Date endTime, Pageable pageable);


    /**
     *
     * @param workOrderNo
     * @param productCode
     * @return
     */
    WorkOrderPo findByWorkOrderNoEqualsAndProductCode(String workOrderNo, String productCode);
    
}
