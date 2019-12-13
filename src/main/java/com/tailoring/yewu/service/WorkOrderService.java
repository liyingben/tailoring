package com.tailoring.yewu.service;

import com.tailoring.yewu.entity.po.WorkOrderPo;
import com.tailoring.yewu.repository.WorkOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class WorkOrderService {


    @Autowired
    private WorkOrderDao workOrderDao;


    public Page<WorkOrderPo> select(String startTime, String endTime, Pageable pageable) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return workOrderDao.findByDueDateBetween(sdf.parse(startTime), sdf.parse(endTime),  pageable);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}