package com.tailoring.yewu.service;

import com.tailoring.yewu.repository.WorkOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TailoringOrderService {


    @Autowired
    private WorkOrderDao workOrderDao;


}