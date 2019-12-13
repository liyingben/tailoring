package com.tailoring.yewu.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID=1L;
    private Integer id;

    private String address;

    private String city;

    private Date createTime;

    private String email;
    @JsonIgnore
    private String password;

    private String realName;

    private Integer roleid;

    private String tel;

    private String username;

    private String s;


}
