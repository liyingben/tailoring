package com.tailoring.yewu.service;

import com.tailoring.yewu.entity.po.TailoringRecoveryRecordPo;
import com.tailoring.yewu.repository.TailoringRecoveryRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TailoringRecoveryRecordService {

    @Autowired
    private TailoringRecoveryRecordDao tailoringRecoveryRecordDao;


    public List<TailoringRecoveryRecordPo> selectByDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return tailoringRecoveryRecordDao.findByCreateTimeBetween(sdf.parse(startTime), sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}