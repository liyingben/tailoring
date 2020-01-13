package com.tailoring.user.service;

import com.tailoring.user.model.Group;
import com.tailoring.user.repository.GroupDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 监控 Service
 * </p>
 *
 * @package: com.tailoring.user.service
 * @description: 监控 Service
 * @author: ben
 * @date: Created in 2018-12-12 00:55
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Slf4j
@Service
public class GroupService {


    @Autowired
    private GroupDao groupDao;

    public List<Group> findAll() {
        return groupDao.findAll();
    }
}
