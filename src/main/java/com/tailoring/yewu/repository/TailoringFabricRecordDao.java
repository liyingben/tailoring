package com.tailoring.yewu.repository;

import com.tailoring.yewu.entity.po.TailoringFabricRecordPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
public interface TailoringFabricRecordDao extends JpaRepository<TailoringFabricRecordPo, Long> {
    /**
     * 根据开始时间和结束时间
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<TailoringFabricRecordPo> findByCreateTimeBetween(Date startTime, Date endTime);

    List<TailoringFabricRecordPo> findByTaskIdEqualsAndSpreadingIdEquals(Long taskId, Long spreadingId);

    List<TailoringFabricRecordPo> findByTaskIdEquals(Long taskId);

    List<TailoringFabricRecordPo> findByTaskIdIn(List<Long> taskIds);

    TailoringFabricRecordPo findBySpreadingIdEqualsAndReelNumberEquals(Long spreadingId, String reelNumber);

    @Query(value = "SELECT left_length FROM tailoring_fabric_record where id = (SELECT max(id) FROM tailoring_fabric_record where reel_number=?1 ) ", nativeQuery = true)
    Double getTheoryLength(String reelNumber);

    @Query(value = "SELECT max(actual_length) FROM tailoring_fabric_record  where reel_number=?1  ", nativeQuery = true)
    Double getActualLength(String reelNumber);

    TailoringFabricRecordPo findFirstByReelNumberEqualsOrderByIdDesc(String reelNumber);
}
