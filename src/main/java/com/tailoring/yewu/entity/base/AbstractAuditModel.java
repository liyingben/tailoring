package com.tailoring.yewu.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 实体通用父类
 * </p>
 *
 * @package: com.tailoring.yewu.entity.base
 * @description: 实体通用父类
 * @author: ben
 * @date: Created in 2018/11/7 14:01
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AbstractAuditModel implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, updatable = false)
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 上次更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", nullable = false)
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private Date updateTime;


    @CreatedBy
    @Column(name = "created_by", updatable = false, length = 64)
    private String createdBy;


    @LastModifiedBy
    @Column(name = "updated_by", length = 64)
    private String updatedBy;
}
