package com.tailoring.yewu.service;


import com.tailoring.yewu.common.TailoringUtils;
import com.tailoring.yewu.entity.dto.BaseDictDto;
import com.tailoring.yewu.entity.po.BaseDictPo;
import com.tailoring.yewu.repository.BaseDictDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

/**
 * 字典
 */
@Service
public class BaseDictService {

    @Autowired
    private BaseDictDao baseDictDao;

    @CachePut(value = "user", key = "#dict.key")
    public void addKey(BaseDictDto dict) {
        BaseDictPo d = baseDictDao.findByKeyEquals(dict.getKey());
        if (d == null) {
            d = new BaseDictPo();
            TailoringUtils.copyProperties(d, dict);
            baseDictDao.save(d);

        } else {
            d.setValue(dict.getValue());
            baseDictDao.save(d);
        }

    }

    public Double getDouble(String key) {
        BaseDictPo dict = baseDictDao.findByKeyEquals(key);
        if (dict == null) {
            return Double.valueOf(0);
        } else {
            return Double.valueOf(dict.getValue());
        }
    }

    //    @Cacheable(value = "user", key = "#key")
    public String getValue(String key) {
        BaseDictPo dict = baseDictDao.findByKeyEquals(key);
        if (dict == null) {
            return null;
        } else {
            return dict.getValue();
        }
    }
}