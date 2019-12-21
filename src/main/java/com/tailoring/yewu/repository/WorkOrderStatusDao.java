package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.WorkOrderStatusPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
public interface WorkOrderStatusDao extends JpaRepository<WorkOrderStatusPo, Long> {

    /**
     *
     * @param workOrderNo
     * @param productCode
     * @return
     */
    WorkOrderStatusPo findByWorkOrderNoEqualsAndProductCode(String workOrderNo, String productCode);
    
}
