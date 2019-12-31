package com.tailoring.yewu.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(name = "base_dict", uniqueConstraints = {@UniqueConstraint(columnNames = {"dict_key"})})
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class BaseDictPo extends AbstractAuditModel {


    @Column(name = "dict_key",length = 50)
    private String key;

    @Column(name = "dict_value",length = 50)
    private String value;

}

