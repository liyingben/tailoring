package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.BaseFabricUsagePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
public interface BaseFabricUsageDao extends JpaRepository<BaseFabricUsagePo, Long> {

    BaseFabricUsagePo findByProductCodeEquals(String productCode);


}
