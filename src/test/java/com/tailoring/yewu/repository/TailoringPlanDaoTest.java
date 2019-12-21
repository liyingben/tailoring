package com.tailoring.yewu.repository;

import com.tailoring.yewu.SpringBootYewuApplicationTests;
import com.tailoring.yewu.entity.po.TailoringPlanPo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
public class TailoringPlanDaoTest extends SpringBootYewuApplicationTests {
    @Autowired
    private TailoringPlanDao tailoringPlanDao;
    @Autowired
    private WorkOrderDao workOrderDao;

    /**
     * 初始化10条数据
     */
    @Test
     public void initData() {


//        WorkOrderPo workOrderPo = WorkOrderPo.builder().workOrderNo("2A-9496").productCode("YY23-T-00-132-02").workOrderQuantity(405d).description("测试数据").dueDate(new Date()).build();
//        workOrderDao.save(workOrderPo);
//        workOrderPo = WorkOrderPo.builder().workOrderNo("2A-9496").productCode("YY23-T-00-132-03").workOrderQuantity(405d).description("测试数据").dueDate(new Date()).build();
//        workOrderDao.save(workOrderPo);
//        workOrderPo = WorkOrderPo.builder().workOrderNo("2A-9496").productCode("YY23-T-00-132-04").workOrderQuantity(405d).description("测试数据").dueDate(new Date()).build();
//        workOrderDao.save(workOrderPo);
//        workOrderPo = WorkOrderPo.builder().workOrderNo("2A-9496").productCode("YY23-T-00-132-05").workOrderQuantity(405d).description("测试数据").dueDate(new Date()).build();
//        workOrderDao.save(workOrderPo);

        TailoringPlanPo tailoringPlanPo = TailoringPlanPo.builder().workOrderNo("2A-9496").productCode("YY23-T-00-132-02").quantity(405).fabricCode("FNA23YEA01").changePiecesQuantity(5).typeNumber("55").status("1").group("测试").member("张三，李四").dueDate(new Date()).typeQuantity(15).build();
        tailoringPlanDao.save(tailoringPlanPo);
        tailoringPlanPo = TailoringPlanPo.builder().workOrderNo("2A-9496").productCode("YY23-T-00-132-03").quantity(405).fabricCode("FNA23YEA01").changePiecesQuantity(5).typeNumber("37").status("1").group("测试").member("张三，李四").dueDate(new Date()).typeQuantity(15).build();
        tailoringPlanDao.save(tailoringPlanPo);
        tailoringPlanPo = TailoringPlanPo.builder().workOrderNo("2A-9496").productCode("YY23-T-00-132-04").quantity(405).fabricCode("FNA23YEA01").changePiecesQuantity(5).typeNumber("38").status("1").group("测试").member("张三，李四").dueDate(new Date()).typeQuantity(15).build();
        tailoringPlanDao.save(tailoringPlanPo);
        tailoringPlanPo = TailoringPlanPo.builder().workOrderNo("2A-9496").productCode("YY23-T-00-132-05").quantity(405).fabricCode("FNA23YEA01").changePiecesQuantity(5).typeNumber("1").status("1").group("测试").member("张三，李四").dueDate(new Date()).typeQuantity(15).build();
        tailoringPlanDao.save(tailoringPlanPo);
    }

}