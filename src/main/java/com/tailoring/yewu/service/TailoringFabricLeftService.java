package com.tailoring.yewu.service;

import com.tailoring.yewu.entity.dto.TailoringFabricInsertDto;
import com.tailoring.yewu.entity.po.TailoringFabricLeftPo;
import com.tailoring.yewu.repository.TailoringFabricLeftDao;
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

    public List<TailoringFabricLeftPo> selectByDate(String startTime, String endTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return tailoringFabricLeftDao.findByCreateTimeBetween(sdf.parse(startTime), sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void save(List<TailoringFabricInsertDto> fabrics) {

        for (TailoringFabricInsertDto dto : fabrics) {
            TailoringFabricLeftPo po = new TailoringFabricLeftPo();
            po.setFabricCode(dto.getFabricCode());
            po.setLotNumber(dto.getLotNumber());
            po.setReelNumber(dto.getReelNumber());
            po.setTheoryLength(dto.getTheoryLength());

            tailoringFabricLeftDao.save(po);
        }

    }

    public Double getTheoryLength(String reelNumber) {

        Double theory_length = tailoringFabricLeftDao.getTheoryLength(reelNumber);
        return theory_length == null ? 0 : theory_length;
    }
}