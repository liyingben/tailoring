package com.tailoring.yewu.service;

import com.tailoring.yewu.common.ResultType;
import com.tailoring.yewu.common.StatusEnum;
import com.tailoring.yewu.entity.dto.TailoringFabricInsertDto;
import com.tailoring.yewu.entity.dto.TailoringInsertDto;
import com.tailoring.yewu.entity.dto.TailoringPlanDto;
import com.tailoring.yewu.entity.po.TailoringDetailPo;
import com.tailoring.yewu.entity.po.TailoringFabricRecordPo;
import com.tailoring.yewu.entity.po.TailoringPlanPo;
import com.tailoring.yewu.entity.vo.TailoringDetailResultVo;
import com.tailoring.yewu.repository.TailoringDetailDao;
import com.tailoring.yewu.repository.TailoringFabricRecordDao;
import com.tailoring.yewu.repository.TailoringPlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TailoringDetailService {

    @Autowired
    private TailoringFabricRecordDao tailoringFabricRecordDao;

    @Autowired
    TailoringPlanDao tailoringPlanDao;

    @Autowired
    private TailoringDetailDao tailoringDetailDao;

    public long insert(TailoringDetailPo po) {
        tailoringDetailDao.save(po);
        return po.getId();
    }

    public long update(TailoringDetailPo po) {
        return tailoringDetailDao.save(po).getId();
    }

    /**
     * 检查
     * 最大可裁剪数量 >=本次裁剪数量.
     * 最大可换片数量 >=本次换片数量。
     * 以及要判断相同版型分组的件数必须相同。
     *
     * @param tailoringPlans
     * @return
     */
    public ResultType checkDetail(List<TailoringPlanDto> tailoringPlans) {
        Map<String, Integer> qMap = new HashMap<>();

        for (TailoringPlanDto dto : tailoringPlans) {
            TailoringPlanPo po = tailoringPlanDao.getOne(dto.getId());
            // 比较件数是否相同
            if (!qMap.containsKey(dto.getTypeGroup())) {
                qMap.put(dto.getTypeGroup(), dto.getQuantity());
            }
            if (dto.getQuantity().intValue() != qMap.get(dto.getTypeGroup()).intValue()) {
                return ResultType.GROUP_QUANTITY_ERROR;
            }

            //比较裁剪数量
            if (dto.getQuantity() > po.getQuantity()) {
                return ResultType.GROUP_GREATER_THAN_MAX_TAILORING_QUANTITY_ERROR;
            } else if ((StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString().equals(po.getStatus()))) {
                TailoringDetailPo tmp = tailoringDetailDao.findByTailoringPlanIdEquals(po.getId());
                if (tmp != null) {
                    if (dto.getQuantity() > po.getQuantity() - tmp.getQuantity()) {
                        return ResultType.GROUP_GREATER_THAN_MAX_TAILORING_QUANTITY_ERROR;
                    }
                }
            }
            //比较换片数量
            if (dto.getChangePiecesQuantity() > po.getChangePiecesQuantity()) {
                return ResultType.GROUP_GREATER_THAN_CHANGE_PIECES_QUANTITY_ERROR;
            } else if ((StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString().equals(po.getStatus()))) {
                TailoringDetailPo tmp = tailoringDetailDao.findByTailoringPlanIdEquals(po.getId());
                if (tmp != null) {
                    if (dto.getChangePiecesQuantity() > po.getChangePiecesQuantity() - tmp.getChangePiecesQuantity()) {
                        return ResultType.GROUP_GREATER_THAN_MAX_TAILORING_QUANTITY_ERROR;
                    }
                }
            }
        }

        return ResultType.OK;
    }

    public TailoringDetailResultVo detail(TailoringInsertDto tailoringInsertDto) {

        //可拉布件数
        int spreadingQuantity = tailoringInsertDto.getSpreadingCount() * tailoringInsertDto.getFabrics().size() * tailoringInsertDto.getQuantity();
        List<TailoringPlanDto> tailoringPlans = tailoringInsertDto.getTailoringPlans();
        Map<String, List<TailoringPlanDto>> tailoringPlansMap = tailoringPlans.stream().collect(Collectors.groupingBy(TailoringPlanDto::getTypeGroup));
        for (String typeGroup : tailoringPlansMap.keySet()) {
            List<TailoringPlanDto> planDtos = tailoringPlansMap.get(typeGroup);
            for (TailoringPlanDto dto : planDtos) {
                //本次裁剪数量
                int currentQuantity = dto.getQuantity();
                //本次换片数量
                int changePiecesQuantity = dto.getChangePiecesQuantity();
                TailoringDetailPo tmp = tailoringDetailDao.findByTailoringPlanIdEquals(dto.getId());
                if (tmp != null) {
                    currentQuantity = currentQuantity - tmp.getQuantity();
                }
                //剩余件数 = 累计的拉布件数 - (本次裁剪数量+本次换片数量)
                int surplusQuantity = spreadingQuantity - currentQuantity - changePiecesQuantity;

                TailoringDetailPo detailPo = tmp != null ? tmp : new TailoringDetailPo();

                // 裁剪计划ID: tailoring_plan_id
                detailPo.setTailoringPlanId(dto.getId());
                //工单号: work_order_no
                detailPo.setWorkOrderNo(dto.getWorkOrderNo());
                //版型分组: type_group
                detailPo.setTypeGroup(dto.getTypeGroup());
                //产品行号: product_line_no
                detailPo.setProductLineNo(dto.getProductLineNo());
                //产品编码: product_code
                detailPo.setProductCode(dto.getProductCode());
                //成品数量: product_quantity
                detailPo.setProductQuantity(null);
                //布料编码: fabric_code
                detailPo.setFabricCode(dto.getFabricCode());
                //总长度（米）: sum_length
                detailPo.setSumLength(null);
                //理论长度（米）: theory_length
                detailPo.setTheoryLength(null);
                //拉布长度（米）: spreading_length
                detailPo.setSpreadingLength(tailoringInsertDto.getSpreadingCount() * tailoringInsertDto.getQuantity());
                //拉布次数: spreading_count
                detailPo.setSpreadingCount(tailoringInsertDto.getSpreadingCount());
                //层数（层高）: floor
                detailPo.setFloor(dto.getFloor());
                //扎单数量: binding_quantity
                detailPo.setBindingQuantity(dto.getBindingQuantity());
                //换片数量: change_pieces_quantity
                detailPo.setChangePiecesQuantity(dto.getChangePiecesQuantity());
                TailoringPlanPo planPo = tailoringPlanDao.getOne(dto.getId());

                if (surplusQuantity > 0) {
                    //件数: quantity = 本次裁剪数量+累计裁剪数量
//                    detailPo.setQuantity(dto.getQuantity() + detailPo.getQuantity());
                    detailPo.setQuantity(dto.getQuantity() );
                    detailPo.setStatus(StatusEnum.TAILORING_DETAIL_STATUS_START.getCode().toString());
                    tailoringDetailDao.save(detailPo);

                    planPo.setStatus(StatusEnum.TAILORING_PLAN_STATUS_FINISH.getCode().toString());
                    planPo.setMaxQuantity(planPo.getMaxQuantity() - dto.getQuantity());
                    tailoringPlanDao.save(planPo);

                } else if (surplusQuantity <= 0) {
                    //    剩余裁剪数量+累计裁剪数量
                    int kcaijian = spreadingQuantity + dto.getQuantity();
//                    detailPo.setQuantity(detailPo.getQuantity() + kcaijian);
                    detailPo.setQuantity( kcaijian);
                    detailPo.setStatus(StatusEnum.TAILORING_DETAIL_STATUS_DEFAULT.getCode().toString());
                    tailoringDetailDao.save(detailPo);

                    planPo.setStatus(StatusEnum.TAILORING_PLAN_STATUS_START.getCode().toString());
                    planPo.setMaxQuantity(planPo.getQuantity() - kcaijian);
                    tailoringPlanDao.save(planPo);
                }
                spreadingQuantity = surplusQuantity;
            }
        }

        List<TailoringFabricInsertDto> fabrics = tailoringInsertDto.getFabrics();
        for (TailoringFabricInsertDto dto : fabrics) {
            TailoringFabricRecordPo po = new TailoringFabricRecordPo();
            po.setFabricCode(dto.getFabricCode());
            po.setLotNumber(dto.getLotNumber());
            po.setReelNumber(dto.getReelNumber());
            po.setActualFabricWidth(dto.getActualFabricWidth());
            po.setTheoryFabricWidth(dto.getTheoryFabricWidth());
            po.setTheoryLength(dto.getTheoryLength());

            tailoringFabricRecordDao.save(po);
        }
        TailoringDetailResultVo po = new TailoringDetailResultVo();
        return po;
    }

    public List<TailoringDetailPo> findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoEquals(String workOrderNo, String productCode, Integer productLineNo) {
        return tailoringDetailDao.findByWorkOrderNoEqualsAndProductCodeEqualsAndProductLineNoEquals(workOrderNo, productCode, productLineNo);
    }


    public List<TailoringDetailPo> selectByDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return tailoringDetailDao.findByCreateTimeBetween(sdf.parse(startTime), sdf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}