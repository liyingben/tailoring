package com.tailoring.yewu.service;


import com.tailoring.yewu.entity.po.BaseFabricDetailPo;
import com.tailoring.yewu.repository.BaseFabricDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaseFabricDetailService {

    @Autowired
    private BaseFabricDetailDao baseFabricDetailDao;

    public List<String> selectFabricCode() {
        List<BaseFabricDetailPo> fabricDetails = baseFabricDetailDao.findAll();
        return fabricDetails.stream().map(BaseFabricDetailPo::getFabricCode).collect(Collectors.toList());
    }
}