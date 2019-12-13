package com.tailoring.yewu.service;


import com.tailoring.yewu.entity.po.BaseHpwPo;
import com.tailoring.yewu.repository.BaseHpwDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseHpwService {

    @Autowired
    private BaseHpwDao baseHpwDao;

    public BaseHpwPo findByProductCodeEquals(String productCode) {
        return baseHpwDao.findByProductCodeEquals(productCode);
    }
}