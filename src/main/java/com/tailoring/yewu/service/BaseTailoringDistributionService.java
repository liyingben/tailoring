package com.tailoring.yewu.service;

import com.tailoring.yewu.entity.po.BaseTailoringDistributionPo;
import com.tailoring.yewu.repository.BaseTailoringDistributionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 裁剪人员组合及超省分配比例表
 */
@Service
public class BaseTailoringDistributionService {

    @Autowired
    private BaseTailoringDistributionDao baseTailoringDistributionDao;

    public List<BaseTailoringDistributionPo> findAll() {
        return baseTailoringDistributionDao.findAll();
    }

}