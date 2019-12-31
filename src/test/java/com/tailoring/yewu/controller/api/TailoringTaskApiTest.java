package com.tailoring.yewu.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tailoring.yewu.SpringBootStartApplicationTests;
import com.tailoring.yewu.common.ActionResult;
import com.tailoring.yewu.entity.dto.TailoringFabricInsertDto;
import com.tailoring.yewu.entity.dto.TailoringTaskPlanDto;
import com.tailoring.yewu.entity.dto.TailoringSpreadingDto;
import com.tailoring.yewu.entity.vo.TailoringSpreadingResultVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TailoringTaskApiTest extends SpringBootStartApplicationTests {
    @Autowired
    TailoringTaskApi tailoringTaskApi;

    @Test
    void createTask() {

        List<TailoringTaskPlanDto> tailoringPlans = createTailoringPlansId();
        ObjectMapper mapper = new ObjectMapper();

        //对象转化为Json
        String json = null;
        try {
            json = mapper.writeValueAsString(tailoringPlans);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(json);
        ActionResult actionResult = tailoringTaskApi.createTask(tailoringPlans);
        System.out.println(actionResult);

    }

    @Test
    void spreading1() {

        Long taskId = 91L;
        //第1次拉布
        TailoringSpreadingDto tailoringSpreadingDto = new TailoringSpreadingDto();
        tailoringSpreadingDto.setTaskId(taskId);
        tailoringSpreadingDto.setSpreadingCount(5);
        tailoringSpreadingDto.setQuantity(20);
        tailoringSpreadingDto.setFloor(5);
        List<TailoringFabricInsertDto> fabrics = creategFabrics();

        tailoringSpreadingDto.setFabrics(fabrics);
        List<TailoringTaskPlanDto> tailoringPlans = createTailoringPlans();
        tailoringSpreadingDto.setTailoringPlans(tailoringPlans);


        ObjectMapper mapper = new ObjectMapper();

        //对象转化为Json
        String json = null;
        try {
            json = mapper.writeValueAsString(tailoringSpreadingDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(json);

        ActionResult<TailoringSpreadingResultVo> result = tailoringTaskApi.spreading(tailoringSpreadingDto);


        try {
            json = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(json);
    }

    @Test
    void spreading2() {

        Long taskId = 100L;
        //第2次拉布
        TailoringSpreadingDto tailoringSpreadingDto = new TailoringSpreadingDto();
        tailoringSpreadingDto.setTaskId(taskId);
        tailoringSpreadingDto.setSpreadingCount(6);
        tailoringSpreadingDto.setQuantity(20);
        tailoringSpreadingDto.setFloor(5);
        List<TailoringFabricInsertDto> fabrics = creategFabrics();

        tailoringSpreadingDto.setFabrics(fabrics);
        List<TailoringTaskPlanDto> tailoringPlans = createTailoringPlansId();
        tailoringSpreadingDto.setTailoringPlans(tailoringPlans);
        ActionResult<TailoringSpreadingResultVo> result = tailoringTaskApi.spreading(tailoringSpreadingDto);

    }

    @Test
    void spreading3() {
        Long taskId = 100L;
        //第3次拉布
        TailoringSpreadingDto tailoringSpreadingDto = new TailoringSpreadingDto();
        tailoringSpreadingDto.setTaskId(taskId);
        tailoringSpreadingDto.setSpreadingCount(5);
        tailoringSpreadingDto.setQuantity(10);
        tailoringSpreadingDto.setFloor(5);
        List<TailoringFabricInsertDto> fabrics = creategFabrics();

        tailoringSpreadingDto.setFabrics(fabrics);
        List<TailoringTaskPlanDto> tailoringPlans = createTailoringPlansId();
        tailoringSpreadingDto.setTailoringPlans(tailoringPlans);
        ActionResult<TailoringSpreadingResultVo> result = tailoringTaskApi.spreading(tailoringSpreadingDto);

    }

    @Test
    void spreading4() {
        Long taskId = 100L;
        //第4次拉布
        TailoringSpreadingDto tailoringSpreadingDto = new TailoringSpreadingDto();
        tailoringSpreadingDto.setTaskId(taskId);
        tailoringSpreadingDto.setSpreadingCount(1);
        tailoringSpreadingDto.setQuantity(10);
        tailoringSpreadingDto.setFloor(2);
        List<TailoringFabricInsertDto> fabrics = creategLastFabrics();

        tailoringSpreadingDto.setFabrics(fabrics);
        List<TailoringTaskPlanDto> tailoringPlans = createTailoringPlansId();
        tailoringSpreadingDto.setTailoringPlans(tailoringPlans);
        ActionResult<TailoringSpreadingResultVo> result = tailoringTaskApi.spreading(tailoringSpreadingDto);
    }


    private List<TailoringFabricInsertDto> creategFabrics() {
        List<TailoringFabricInsertDto> fabrics = new ArrayList<>();
        TailoringFabricInsertDto dto = new TailoringFabricInsertDto();
        dto.setTheoryLength(800.0);
        dto.setTheoryFabricWidth(1.5);
        dto.setReelNumber("4000RA9B04E1R1T19056L2B01");
        dto.setLotNumber("9B04L2");
        fabrics.add(dto);
        dto = new TailoringFabricInsertDto();
        dto.setTheoryLength(800.0);
        dto.setTheoryFabricWidth(1.5);
        dto.setReelNumber("4000RA9B04E1R1T19056L2B02");
        dto.setLotNumber("9B04L2");
        fabrics.add(dto);
        dto = new TailoringFabricInsertDto();
        dto.setTheoryLength(800.0);
        dto.setTheoryFabricWidth(1.5);
        dto.setReelNumber("4000RA9B04E1R1T19056L2B03");
        dto.setLotNumber("9B04L2");
        fabrics.add(dto);
        dto = new TailoringFabricInsertDto();
        dto.setTheoryLength(800.0);
        dto.setTheoryFabricWidth(1.5);
        dto.setReelNumber("4000RA9B04E1R1T19056L2B04");
        dto.setLotNumber("9B04L2");
        fabrics.add(dto);
        dto = new TailoringFabricInsertDto();
        dto.setTheoryLength(800.0);
        dto.setTheoryFabricWidth(1.5);
        dto.setReelNumber("4000RA9B04E1R1T19056L2B05");
        dto.setLotNumber("9B04L2");
        fabrics.add(dto);
        return fabrics;
    }

    private List<TailoringFabricInsertDto> creategLastFabrics() {
        List<TailoringFabricInsertDto> fabrics = new ArrayList<>();
        TailoringFabricInsertDto dto = new TailoringFabricInsertDto();
        dto.setTheoryLength(800.0);
        dto.setTheoryFabricWidth(1.5);
        dto.setReelNumber("4000RA9B04E1R1T19056L2B01");
        dto.setLotNumber("9B04L2");
        fabrics.add(dto);
        dto = new TailoringFabricInsertDto();
        dto.setTheoryLength(800.0);
        dto.setTheoryFabricWidth(1.5);
        dto.setReelNumber("4000RA9B04E1R1T19056L2B02");
        dto.setLotNumber("9B04L2");
        fabrics.add(dto);
        dto = new TailoringFabricInsertDto();
        dto.setTheoryLength(800.0);
        dto.setTheoryFabricWidth(1.5);
        dto.setReelNumber("4000RA9B04E1R1T19056L2B03");
        dto.setLotNumber("9B04L2");
//        dto.setType(1);
        fabrics.add(dto);
        dto = new TailoringFabricInsertDto();
        dto.setTheoryLength(800.0);
        dto.setTheoryFabricWidth(1.5);
        dto.setReelNumber("4000RA9B04E1R1T19056L2B04");
        dto.setLotNumber("9B04L2");
//        dto.setType(2);
        fabrics.add(dto);
        dto = new TailoringFabricInsertDto();
        dto.setTheoryLength(800.0);
        dto.setTheoryFabricWidth(1.5);
        dto.setReelNumber("4000RA9B04E1R1T19056L2B05");
        dto.setLotNumber("9B04L2");
//        dto.setType(3);
        fabrics.add(dto);
        return fabrics;
    }


    private List<TailoringTaskPlanDto> createTailoringPlans() {
        List<TailoringTaskPlanDto> tailoringPlans = new ArrayList<>();
        TailoringTaskPlanDto dto = new TailoringTaskPlanDto();
        dto.setWorkOrderNo("2A-9496");
        dto.setProductCode("YY23-T-00-132-02");
        dto.setTypeGroup("1");
        dto.setQuantity(405);
        dto.setChangePiecesQuantity(5);
        dto.setTypeQuantity(15);
        dto.setFloor(5);
        tailoringPlans.add(dto);
        dto = new TailoringTaskPlanDto();
        dto.setQuantity(405);
        dto.setWorkOrderNo("2A-9496");
        dto.setProductCode("YY23-T-00-132-03");
        dto.setChangePiecesQuantity(5);
        dto.setTypeGroup("1");
        dto.setTypeQuantity(15);
        dto.setFloor(5);
        tailoringPlans.add(dto);
        dto = new TailoringTaskPlanDto();
        dto.setWorkOrderNo("2A-9496");
        dto.setProductCode("YY23-T-00-132-04");
        dto.setQuantity(405);
        dto.setChangePiecesQuantity(5);
        dto.setTypeGroup("2");
        dto.setTypeQuantity(5);
        dto.setFloor(5);
        tailoringPlans.add(dto);
        dto = new TailoringTaskPlanDto();
        dto.setQuantity(405);
        dto.setWorkOrderNo("2A-9496");
        dto.setProductCode("YY23-T-00-132-05");
        dto.setChangePiecesQuantity(5);
        dto.setTypeGroup("3");
        dto.setTypeQuantity(5);
        dto.setFloor(5);
        tailoringPlans.add(dto);
        return tailoringPlans;
    }

    private List<TailoringTaskPlanDto> createTailoringPlansId() {
        List<TailoringTaskPlanDto> tailoringPlans = new ArrayList<>();
        TailoringTaskPlanDto dto = new TailoringTaskPlanDto();
        dto.setWorkOrderNo("2A-9496");
        dto.setProductCode("YY23-T-00-132-02");
        dto.setTypeGroup("1");
        dto.setQuantity(405);
        dto.setChangePiecesQuantity(5);
        dto.setTypeQuantity(15);
        dto.setId(99l);
        dto.setFloor(5);
        tailoringPlans.add(dto);
        dto = new TailoringTaskPlanDto();
        dto.setQuantity(405);
        dto.setWorkOrderNo("2A-9496");
        dto.setProductCode("YY23-T-00-132-03");
        dto.setChangePiecesQuantity(5);
        dto.setTypeGroup("1");
        dto.setTypeQuantity(15);
        dto.setId(100l);
        dto.setFloor(5);
        tailoringPlans.add(dto);
        dto = new TailoringTaskPlanDto();
        dto.setWorkOrderNo("2A-9496");
        dto.setProductCode("YY23-T-00-132-04");
        dto.setQuantity(405);
        dto.setChangePiecesQuantity(5);
        dto.setTypeGroup("2");
        dto.setTypeQuantity(5);
        dto.setId(101l);
        dto.setFloor(5);
        tailoringPlans.add(dto);
        dto = new TailoringTaskPlanDto();
        dto.setQuantity(405);
        dto.setWorkOrderNo("2A-9496");
        dto.setProductCode("YY23-T-00-132-05");
        dto.setChangePiecesQuantity(5);
        dto.setTypeGroup("3");
        dto.setTypeQuantity(5);
        dto.setId(102l);
        dto.setFloor(5);
        tailoringPlans.add(dto);
        return tailoringPlans;
    }

}