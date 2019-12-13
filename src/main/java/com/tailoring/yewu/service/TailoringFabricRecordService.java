package com.tailoring.yewu.service;

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
public class TailoringFabricRecordService {

    @Autowired
    private TailoringFabricRecordDao tailoringFabricRecordDao;
    @Autowired
    private TailoringFabricLeftDao tailoringFabricLeftDao;

    public long insert(TailoringFabricRecordPo po) {
        tailoringFabricRecordDao.save(po);
        return po.getId();
    }

    public long update(TailoringFabricRecordPo po) {
        return tailoringFabricRecordDao.save(po).getId();
    }

    public int delete(long id) {

        TailoringFabricRecordPo po = tailoringFabricRecordDao.getOne(id);
        TailoringFabricLeftPo leftPo = new TailoringFabricLeftPo();
        leftPo.setFabricCode(po.getFabricCode());
        leftPo.setLotNumber(po.getLotNumber());
        leftPo.setReelNumber(po.getReelNumber());
        tailoringFabricLeftDao.save(leftPo);

        tailoringFabricRecordDao.deleteById(po.getId());

        return 1;
    }

    public List<TailoringFabricRecordPo> select(TailoringFabricRecordPo query) {
        return tailoringFabricRecordDao.findAll();
    }

    public List<TailoringFabricRecordPo> selectByDate(String startTime, String endTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return tailoringFabricRecordDao.findByCreateTimeBetween(sdf.parse(startTime), sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
    }
    }
}