package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 审核表
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tailoring_examine_no")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class TailoringExamineNoPo extends AbstractAuditModel {
    /**
     * 裁剪编号: tailoring_no
     * NO:每个布料使用记录生成一个流水号：年+S+流水号。例：19S2627.
     */
    @Column(name = "number_id")
    private Integer numberId;

}

