package com.tailoring.yewu.service;

import cn.hutool.core.util.IdUtil;
import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.common.StatusEnum;
import com.tailoring.yewu.entity.dto.TailoringFabricInsertDto;
import com.tailoring.yewu.entity.dto.TailoringFabricLeftDto;
import com.tailoring.yewu.entity.dto.TailoringFabricLeftsDto;
import com.tailoring.yewu.entity.dto.TailoringTaskPlanDto;
import com.tailoring.yewu.entity.po.BaseFabricDetailPo;
import com.tailoring.yewu.entity.po.TailoringFabricLeftPo;
import com.tailoring.yewu.entity.po.TailoringFabricRecordPo;
import com.tailoring.yewu.entity.vo.TailoringFabricLeftTheoryLengthVo;
import com.tailoring.yewu.entity.vo.TailoringSpreadingResultVo;
import com.tailoring.yewu.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TailoringFabricLeftService {

    @Autowired
    private TailoringFabricLeftDao tailoringFabricLeftDao;
    @Autowired
    private TailoringFabricRecordDao tailoringFabricRecordDao;
    @Autowired
    TailoringTaskPlanDao tailoringTaskPlanDao;
    @Autowired
    BaseFabricDetailDao baseFabricDetailDao;

    public List<TailoringFabricLeftPo> selectByDate(String startTime, String endTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return tailoringFabricLeftDao.findByCreateTimeBetween(sdf.parse(startTime), sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ActionResult save(TailoringFabricLeftsDto dto){
        StatusEnum statusEnum= checkLengthExceeded( dto.getFabrics());

        if(statusEnum!=null){
            ActionResult actionResult = new ActionResult(statusEnum);
            return actionResult;
        }
       return  save(dto.getFabrics());

    }
    /**
     * 保存布头数据
     * 0 正常，1：布头，2：废弃,3：用完
     * @param fabrics
     * @return
     */
    public ActionResult save(List<TailoringFabricLeftDto> fabrics) {




        String uuid = IdUtil.fastSimpleUUID();
        for (TailoringFabricLeftDto dto : fabrics) {

            TailoringFabricLeftPo po = tailoringFabricLeftDao.findByReelNumberEquals(dto.getReelNumber());
            if (po == null) {
                po = new TailoringFabricLeftPo();
            }

            TailoringFabricRecordPo recordPo = tailoringFabricRecordDao.findBySpreadingIdEqualsAndReelNumberEquals(dto.getSpreadingId(), dto.getReelNumber());
            po.setFabricCode(dto.getFabricCode());
            po.setLotNumber(dto.getLotNumber());
            po.setReelNumber(dto.getReelNumber());
            po.setSpreadingId(dto.getSpreadingId());
            if (recordPo != null) {
                po.setTheoryLength(recordPo.getLeftLength().doubleValue());
                po.setActualFabricWidth(recordPo.getActualFabricWidth());
                po.setTheoryFabricWidth(recordPo.getTheoryFabricWidth());
            } else {
                po.setTheoryLength(dto.getTheoryLength());
                po.setActualFabricWidth(dto.getActualFabricWidth());
                po.setTheoryFabricWidth(dto.getTheoryFabricWidth());
            }
            po.setType(dto.getType()+"");
            po.setUuid(uuid);
            tailoringFabricLeftDao.save(po);
        }
        return new ActionResult();
    }

    /**
     * 检查布料是否剩余太多
     * 0 正常，1：布头，2：废弃,3：用完
     * 如果是废弃或用完要监测布料是否剩余太多浪费
     * @param fabrics
     * @return
     */
    private StatusEnum checkLengthExceeded(List<TailoringFabricLeftDto> fabrics) {
        //2：废弃,3：用完
        List<TailoringFabricLeftDto> delOrUseUp = fabrics.stream().filter(
                d -> d.getType()==2||d.getType()==3
        ).collect(Collectors.toList());
        /**
         * 当输入拉布次数并计算出理论长度时，
         * 计算“实际长度”（实际长度=层高*长度）。层高=拉布次数*卷数。计算“理论长度”。上一次的理论长度-实际长度。
         * 1，检查实际长度>理论长度+差异系数时，提示用户：拉布长度超出理论长度太多，请检查数据是否正确，如果点"是"，则保存对应的记录。如果点“否”则返回到扫码界面输入界面，员工修正数据后点击保存后再进行检查保存。
         * 2、当员工点击该布料用完时:计算理论长度<理论长度-差异系数时 ，提示实际长度小于理论长度，请检查数据是否正确，点击“是” 则进行保存，点击“否”则返回之前的页面进行修正数据。
         * 3、当员工删除该卷时：计算理论长度<理论长度-差异系数时 ，提示实际长度小于理论长度，请确认是否用完？点击“是”则删除该卷，并记录对应信息。点击“否”则返回当前扫码界面。
         */

        /**
         * 情况1
         */
        for (TailoringFabricLeftDto dto : delOrUseUp) {

            TailoringFabricRecordPo po = tailoringFabricRecordDao.findFirstByReelNumberEqualsOrderByIdDesc(dto.getReelNumber());
            BaseFabricDetailPo baseFabricDetailPo = baseFabricDetailDao.findFirstByFabricCodeEquals(dto.getFabricCode());
            //最小差异系数
            if (po.getSpreadingLength() < po.getLeftLength() - baseFabricDetailPo.getFabricLengthDifference()) {
                  return StatusEnum.LEFT_STATUS_ACTUAL_LENGTH_LESS_THAN_THEORETICAL_LENGTH;
            }
        }

        return null;
    }

    public TailoringFabricLeftTheoryLengthVo getTheoryLengthAndLeftList(String reelNumber) {
        TailoringFabricLeftTheoryLengthVo vo = new TailoringFabricLeftTheoryLengthVo();
        vo.setTheoryLength(0.0);
        vo.setFagEndList(new ArrayList<>());
        TailoringFabricLeftPo leftPo = tailoringFabricLeftDao.findByReelNumberEquals(reelNumber);
        if(leftPo==null){
            Double theoryLength2 = tailoringFabricRecordDao.getTheoryLength(reelNumber);
            if (theoryLength2 != null) {
                vo.setTheoryLength(theoryLength2);
            }
            return vo;
        }
        if(!"1".equals(leftPo.getType())){
            return vo;
        }

        vo.setTheoryLength(leftPo.getTheoryLength());
        vo.setFagEndList(tailoringFabricLeftDao.findByUuidEqualsAndTypeEquals(leftPo.getUuid(), "1"));
        return vo;

    }

}