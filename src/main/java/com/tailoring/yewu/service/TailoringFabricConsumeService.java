package com.tailoring.yewu.service;

import com.tailoring.yewu.entity.po.TailoringFabricConsumePo;
import com.tailoring.yewu.repository.TailoringFabricConsumeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TailoringFabricConsumeService {

    @Autowired
    private TailoringFabricConsumeDao tailoringFabricConsumeDao;

    public TailoringFabricConsumePo select(TailoringFabricConsumePo query) {
        return tailoringFabricConsumeDao.getOne(query.getId());
    }

    public List<TailoringFabricConsumePo> selectByDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return tailoringFabricConsumeDao.findByCreateTimeBetween(sdf.parse(startTime), sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}