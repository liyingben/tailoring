package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.BaseFabricDetailPo;
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
 * @author: ben
 * @date: Created in 2018/11/7 14:07
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Repository
public interface BaseFabricDetailDao extends JpaRepository<BaseFabricDetailPo, Long> {

    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<BaseFabricDetailPo> findByCreateTimeBetween(Date startTime, Date endTime);

    BaseFabricDetailPo findFirstByFabricCodeEquals(String fabricCode);
}
