package com.tailoring.yewu.entity.dto;

import com.tailoring.yewu.entity.base.AbstractAuditModel;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto extends AbstractAuditModel {
    private static final long serialVersionUID=1L;


    private String address;

    private String city;

    private Date createTime;

    private String email;

    private String password;

    private String realName;

    private Integer roleid;

    private String tel;

    private String username;

    private String s;


}
