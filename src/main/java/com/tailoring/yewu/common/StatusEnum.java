package com.tailoring.yewu.common;

public enum StatusEnum {

	//裁剪计划状态
	TAILORING_PLAN_STATUS_DELETE(-1, "删除状态"),
	TAILORING_PLAN_STATUS_DEFAULT(1, "正常状态，等待裁剪"),
	TAILORING_PLAN_STATUS_START(2, "开始裁剪"),
	TAILORING_PLAN_STATUS_OVER(3, "裁剪完成"),
	TAILORING_PLAN_STATUS_SUBMIT(4, "提交"),
	TAILORING_PLAN_STATUS_FINISH(5, "裁剪完成状态"),

	//裁剪计划状态
	TAILORING_DETAIL_STATUS_DELETE(-1, "删除状态"),
	TAILORING_DETAIL_STATUS_DEFAULT(1, "正常状态"),
	TAILORING_DETAIL_STATUS_START(2, "裁剪完成等待审核"),
	TAILORING_DETAILSTATUS_FINISH(3, "完成状态"),

	//工单状态状态
	TAILORING_ORDER_STATUS_DELETE(-1, "删除状态"),
	TAILORING_ORDER_STATUS_DEFAULT(1, "正常状态"),
	TAILORING_ORDERSTATUS_START(2, "开始创建计划"),
	TAILORING_ORDERSTATUS_OVER(3, "开始创建计划"),
	TAILORING_ORDER_STATUS_FINISH(4, "计划创建完成");






	private Integer code;
	private String desc;

	StatusEnum(Integer code, String desc) {
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
