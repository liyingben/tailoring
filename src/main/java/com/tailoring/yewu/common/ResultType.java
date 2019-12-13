package com.tailoring.yewu.common;

public enum ResultType {

	OK(200, "成功"),

	FAILURE(61210, "请求失败"),

	NO_TOKEN(61310, "未登录"),

	BAD_REQUEST(61400, "参数错误"),

	NOT_FOUND(61404, "请求地址错误"),

	NO_DATA(70000, "数据库不存在该数据"),

	NO_AUTH_DATA(70001, "无权限操作该数据"),

	INTERNAL_ERROR(61500, "服务器内部错误"),

	NAME_REPEAT(70002, "名称重复"),

	OPERATE_ISDELETED(70003, "该条动态已删除"),

	START_END_TIME_ERROR(70004,"开始时间必须早于结束时间"),
	LOGIN_FALL(9019997, "用户名或密码错误"),
	DATA_EXSIT(70006,"数据库已存在该数据"),





	DELETED(70001, "数据已删除"),

	BAD_PARAM_REQUEST(901000, "参数错误请求"),

	TOKEN_NOT_EXIST(901900, "token不存在或已过期"),



	FORBIDDEN(901403, "服务拒绝执行"),

	REPEAT_SUB(901405, "重复提交"),

	NOT_SUPPORTED(901505, "非法连接"),

	PAGE_NOT_FOUND(901404, "页面未找到"),

	UNKNOWN(901101, "未知错误"),

	FAILE(9019999, "系统错误"),

	CAPTCHA_FAIL(9019998, "验证码错误"),


	THIRD_PARTY_FAILE(9019000, "第三方接口错误"),

	DATA_NO_EXISTS(9019995, "数据记录不存在"),

	DATA_EXISTS(9019996, "数据记录已存在，重复请求"),

	REDIS_ERROR(901602, "redis操作错误"),

	REMOTE_VALIDATE_ERROR(901607, "调用认证中心出错"),

	ERROR_DB(901502, "数据库错误"),

	NO_UPDATE_ERROR_DB(901503, "数据库未更新"),

	ROLE_DEFINED_ERROR(901101, "角色验证错误"),

	ROLE_PARAM_ERROR(901102, "角色参数错误"),

	USER_DEFINED_ERROR(901100, "%s"),

	GROUP_QUANTITY_ERROR(800101, "同版型分组的件数不同"),
	GROUP_GREATER_THAN_MAX_TAILORING_QUANTITY_ERROR(800102, "本次裁剪数量大于最大可裁剪数量"),
	GROUP_GREATER_THAN_CHANGE_PIECES_QUANTITY_ERROR(800103, "本次换片数量大于换片数量");


	private Integer code;
	private String desc;

	ResultType(Integer code, String desc) {
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
