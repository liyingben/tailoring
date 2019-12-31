package com.tailoring.yewu.service;

import com.tailoring.yewu.entity.po.TailoringDetailPo;
import com.tailoring.yewu.repository.TailoringDetailDao;
import com.tailoring.yewu.repository.TailoringPlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TailoringDetailService {

    @Autowired
    TailoringPlanDao tailoringPlanDao;

    @Autowired
    private TailoringDetailDao tailoringDetailDao;

    public long update(TailoringDetailPo po) {
        return tailoringDetailDao.save(po).getId();
    }

    public List<TailoringDetailPo> selectByDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return tailoringDetailDao.findByCreateTimeBetween(sdf.parse(startTime), sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}