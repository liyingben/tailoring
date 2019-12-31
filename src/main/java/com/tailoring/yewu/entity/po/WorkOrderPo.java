package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "v_erp_prod_order")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class WorkOrderPo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 工单号码
     */
    @Column(name = "forder" ,length = 30)
    private String workOrderNo;
    /**
     * 产品行号
     */
    @Column(name = "flineno",length = 11)
    private String productLineNo;
    /**
     * 产品编号
     */
    @Column(name = "fcode",length = 50)
    private String productCode;
    /**
     * 描述
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * 工单数量
     */
    @Column(name = "fquantity")
    private Double workOrderQuantity;
    /**
     *
     */
    @Column(name = "remainingquantity")
    private Double remainingQuantity;
    /**
     * 装箱数量
     */
    @Column(name = "fbox")
    private Double boxQuantity;
    /**
     * 扎单数量
     */
    @Column(name = "fpcs")
    private Double bindingQuantity;
    /**
     * 车间
     * ERP视图增加“车间”字段：FDEPT(CHAR，10)
     */
    @Column(name = "dept",length = 10)
    private String dept;

    /**
     * 出货日期
     */
    @Column(name = "fduedate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dueDate;

}