package com.tailoring.yewu.repository;

import cn.hutool.core.util.IdUtil;
import com.tailoring.yewu.SpringBootStartApplicationTests;
import com.tailoring.yewu.entity.po.TailoringTaskPo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * jpa 测试类
 * </p>
 *
 * @package: com.tailoring.yewu.repository
 * @description: jpa 测试类
 * @author: yangkai.shen
 * @date: Created in 2018/11/7 14:09
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
public class TailoringDaoTest extends SpringBootStartApplicationTests {
    @Autowired
    private TailoringTaskDao tailoringDao;

    /**
     * 测试保存
     */
    @Test
    public void testSave() {
        String salt = IdUtil.fastSimpleUUID();
        TailoringTaskPo testSave3 = TailoringTaskPo.builder().build();
        tailoringDao.save(testSave3);

        Assert.assertNotNull(testSave3.getId());
        Optional<TailoringTaskPo> byId = tailoringDao.findById(testSave3.getId());
        Assert.assertTrue(byId.isPresent());
        log.debug("【byId】= {}", byId.get());
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        long count = tailoringDao.count();
        tailoringDao.deleteById(1L);
        long left = tailoringDao.count();
        Assert.assertEquals(count - 1, left);
    }

    /**
     * 测试修改
     */
    @Test
    public void testUpdate() {
        tailoringDao.findById(1L).ifPresent(user -> {
//            user.setCheckName("JPA修改名字");
            tailoringDao.save(user);
        });
//        Assert.assertEquals("JPA修改名字", tailoringDao.findById(1L).get.getName());
    }

    /**
     * 测试查询单个
     */
    @Test
    public void testQueryOne() {
        Optional<TailoringTaskPo> byId = tailoringDao.findById(1L);
        Assert.assertTrue(byId.isPresent());
        log.debug("【byId】= {}", byId.get());
    }

    /**
     * 测试查询所有
     */
    @Test
    public void testQueryAll() {
        List<TailoringTaskPo> users = tailoringDao.findAll();
        Assert.assertNotEquals(0, users.size());
        log.debug("【users】= {}", users);
    }

    /**
     * 测试分页排序查询
     */
    @Test
    public void testQueryPage() {
        // 初始化数据
        initData();
        // JPA分页的时候起始页是页码减1
        Integer currentPage = 0;
        Integer pageSize = 5;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sort);
        Page<TailoringTaskPo> userPage = tailoringDao.findAll(pageRequest);

        Assert.assertEquals(5, userPage.getSize());
        Assert.assertEquals(tailoringDao.count(), userPage.getTotalElements());
        log.debug("【id】= {}", userPage.getContent().stream().map(TailoringTaskPo::getId).collect(Collectors.toList()));
    }

    /**
     * 初始化10条数据
     */
    private void initData() {
        List<TailoringTaskPo> userList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            String salt = IdUtil.fastSimpleUUID();
            int index = 3 + i;
            TailoringTaskPo user = TailoringTaskPo.builder().build();
            userList.add(user);
        }
        tailoringDao.saveAll(userList);
    }

}