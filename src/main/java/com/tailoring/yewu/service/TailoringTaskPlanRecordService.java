package com.tailoring.yewu.service;

import com.tailoring.yewu.entity.po.TailoringTaskPlanRecordPo;
import com.tailoring.yewu.repository.TailoringTaskPlanRecordDao;
import com.tailoring.yewu.repository.TailoringPlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TailoringTaskPlanRecordService {

    @Autowired
    TailoringPlanDao tailoringPlanDao;

    @Autowired
    private TailoringTaskPlanRecordDao tailoringTaskPlanRecordDao;

    public long update(TailoringTaskPlanRecordPo po) {
        return tailoringTaskPlanRecordDao.save(po).getId();
    }

    public List<TailoringTaskPlanRecordPo> selectByDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return tailoringTaskPlanRecordDao.findByCreateTimeBetween(sdf.parse(startTime), sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}