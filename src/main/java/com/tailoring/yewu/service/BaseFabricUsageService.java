package com.tailoring.yewu.service;

import com.tailoring.yewu.entity.po.BaseFabricUsagePo;
import com.tailoring.yewu.repository.BaseFabricUsageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 产品布料用量
 */
@Service
public class BaseFabricUsageService {

    @Autowired
    private BaseFabricUsageDao baseFabricUsageDao;

    public BaseFabricUsagePo findByProductCodeEquals(String productCode) {
        return baseFabricUsageDao.findByProductCodeEquals(productCode);
    }

    /**
     * 返回布料版型的map，key是布料编码
     * @return
     */
    public Map mapTypeNumberByfabricCode() {
        List<BaseFabricUsagePo> list = baseFabricUsageDao.findAll();
        Map<String, Set<String>>  fabricCodeMap = new HashMap();
        for(BaseFabricUsagePo po:list){
            if(!fabricCodeMap.containsKey(po.getFabricCode()))
                fabricCodeMap.put(po.getFabricCode(),new TreeSet<String>());
            fabricCodeMap.get(po.getFabricCode()).add(po.getTypeNumber());
        }

        return fabricCodeMap;
    }
}