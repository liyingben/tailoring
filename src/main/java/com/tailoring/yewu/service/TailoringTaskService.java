package com.tailoring.yewu.service;

import com.tailoring.yewu.common.StatusEnum;
import com.tailoring.yewu.entity.dto.TailoringTaskPassDto;
import com.tailoring.yewu.entity.po.*;
import com.tailoring.yewu.entity.vo.TailoringTaskVo;
import com.tailoring.yewu.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TailoringTaskService {

    @Autowired
    private TailoringTaskDao tailoringTaskDao;

    @Autowired
    private TailoringFabricRecordDao tailoringFabricRecordDao;

    @Autowired
    TailoringPlanDao tailoringPlanDao;
    @Autowired
    private TailoringDetailDao tailoringDetailDao;
    @Autowired
    private TailoringFabricConsumeDao tailoringFabricConsumeDao;

    public long insert(TailoringTaskPo tailoringPo) {
        tailoringTaskDao.save(tailoringPo);
        return tailoringPo.getId();
    }
    public void examine() {
        List<TailoringDetailPo>  tailoringTaskPos = tailoringDetailDao.findByStatusEquals("1");

        for(TailoringDetailPo po:tailoringTaskPos){
            TailoringTaskPo tailoringTaskPo = new TailoringTaskPo();
           
            tailoringTaskPo.setCheckDate(new Date());
            tailoringTaskPo.setErpdatum(new Date());
            tailoringTaskPo.setCheckName("张三");
            tailoringTaskDao.save(tailoringTaskPo);
        }

    }

    public List<TailoringTaskPo> selectByDate(String startTime, String endTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return tailoringTaskDao.findByCreateTimeBetween(sdf.parse(startTime), sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 审核详情
     * @param id
     * @return
     */
    public TailoringTaskVo examineDetails(Long id) {
        TailoringDetailPo tailoringDetailPo= tailoringDetailDao.getOne(id) ;
        List<TailoringDetailPo> tailoringDetails = new ArrayList<>();
        tailoringDetails.add(tailoringDetailPo);
        TailoringTaskVo tailoringTaskVo = new TailoringTaskVo();
        tailoringTaskVo.setTailoringDetails(tailoringDetails);
        TailoringPlanPo tailoringPlanPo= tailoringPlanDao.getOne(tailoringDetailPo.getTailoringPlanId()) ;
        tailoringTaskVo.setTailoringPlan(tailoringPlanPo);
        List<TailoringFabricRecordPo>  tailoringFabricRecords = tailoringFabricRecordDao.findAll();
        tailoringTaskVo.setTailoringFabricRecords(tailoringFabricRecords);
        List<TailoringFabricConsumePo> tailoringFabricConsumes = tailoringFabricConsumeDao.findAll();
        tailoringTaskVo.setTailoringFabricConsumes(tailoringFabricConsumes);
        return tailoringTaskVo;
    }
    public Integer  examinePass(TailoringTaskPassDto passDto) {
        TailoringTaskPo tailoringTaskPo = tailoringTaskDao.getOne(passDto.getId());
        tailoringTaskPo.setCheckName(passDto.getCheckName());
        tailoringTaskPo.setStatus("2");
        tailoringTaskDao.save(tailoringTaskPo);

        List<TailoringDetailPo> tailoringDetailPos = tailoringDetailDao.findByTaskIdEquals(tailoringTaskPo.getId());


        tailoringDetailPos.forEach(w ->{
            TailoringPlanPo tailoringPlanPo= tailoringPlanDao.getOne(w.getTailoringPlanId());
            tailoringPlanPo.setStatus(StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString());
            tailoringPlanDao.save(tailoringPlanPo);
        });
        return 1;
    }


}