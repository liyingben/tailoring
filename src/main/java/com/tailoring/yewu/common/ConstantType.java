package com.tailoring.yewu.common;

public enum ConstantType {


	FABRIC_CODE_ALL(2, "全部");

	private Integer code;
	private String desc;

	ConstantType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

}
