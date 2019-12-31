package com.tailoring.yewu.service;

import cn.hutool.core.util.IdUtil;
import com.tailoring.yewu.entity.dto.TailoringFabricLeftDto;
import com.tailoring.yewu.entity.po.TailoringFabricLeftPo;
import com.tailoring.yewu.entity.po.TailoringFabricRecordPo;
import com.tailoring.yewu.repository.TailoringFabricLeftDao;
import com.tailoring.yewu.repository.TailoringFabricRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TailoringFabricLeftService {

    @Autowired
    private TailoringFabricLeftDao tailoringFabricLeftDao;
    @Autowired
    private TailoringFabricRecordDao tailoringFabricRecordDao;

    public List<TailoringFabricLeftPo> selectByDate(String startTime, String endTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return tailoringFabricLeftDao.findByCreateTimeBetween(sdf.parse(startTime), sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void save(List<TailoringFabricLeftDto> fabrics) {

        String uuid = IdUtil.fastSimpleUUID();
        for (TailoringFabricLeftDto dto : fabrics) {
            if (dto.getTheoryLength() <= 0) {
                continue;
            }
            TailoringFabricLeftPo po = tailoringFabricLeftDao.findByReelNumberEquals(dto.getReelNumber());
            if (po == null) {
                po = new TailoringFabricLeftPo();
            }

            TailoringFabricRecordPo recordPo = tailoringFabricRecordDao.findBySpreadingIdEqualsAndReelNumberEquals(dto.getSpreadingId(), dto.getReelNumber());
            po.setFabricCode(dto.getFabricCode());
            po.setLotNumber(dto.getLotNumber());
            po.setReelNumber(dto.getReelNumber());
            po.setSpreadingId(dto.getSpreadingId());
            if (recordPo != null) {
                po.setTheoryLength(recordPo.getLeftLength().doubleValue());
                po.setActualFabricWidth(recordPo.getActualFabricWidth());
                po.setTheoryFabricWidth(recordPo.getTheoryFabricWidth());
            } else {
                po.setTheoryLength(dto.getTheoryLength());
                po.setActualFabricWidth(dto.getActualFabricWidth());
                po.setTheoryFabricWidth(dto.getTheoryFabricWidth());
            }
            po.setUuid(uuid);
            tailoringFabricLeftDao.save(po);
        }

    }

    public Double getTheoryLength(String reelNumber) {

        Double theoryLength1 = tailoringFabricLeftDao.getTheoryLength(reelNumber);
        Double theoryLength2 = tailoringFabricRecordDao.getTheoryLength(reelNumber);
        if (theoryLength1 == null && theoryLength2 == null) {
            return 0.0;
        }
        if (theoryLength1 == null) {
            return theoryLength2;
        }
        if (theoryLength2 == null) {
            return theoryLength1;
        }
        return Math.min(theoryLength1, theoryLength2);
    }

    public List<TailoringFabricLeftPo> getFabricLefts(String reelNumber) {

        TailoringFabricLeftPo po = tailoringFabricLeftDao.findByReelNumberEquals(reelNumber);
        if (po == null) {
            return new ArrayList<>();
        } else {
            return tailoringFabricLeftDao.findByUuidEqualsAndTypeEquals(po.getUuid(), "1");
        }
    }
}