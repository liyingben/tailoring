package com.tailoring.yewu.common;

public enum StatusEnum {


    //工单
    TAILORING_ORDER_STATUS_DELETE(-1, "删除"),
    TAILORING_ORDER_STATUS_DEFAULT(1, "初始"),
    TAILORING_ORDER_STATUS_START(2, "创建计划"),
    TAILORING_ORDER_STATUS_OVER(3, "开始作业"),
    TAILORING_ORDER_STATUS_FINISH(4, "计划完成"),

    //裁剪计划
    TAILORING_PLAN_STATUS_DELETE(-1, "删除"),
    TAILORING_PLAN_STATUS_DEFAULT(1, "初始"),
    TAILORING_PLAN_STATUS_WAIT(2, "等待裁剪"),
    TAILORING_PLAN_STATUS_START(3, "开始裁剪"),
    TAILORING_PLAN_STATUS_OVER(4, "裁剪完成"),
    TAILORING_PLAN_STATUS_SUBMIT(5, "提交"),
    TAILORING_PLAN_STATUS_FINISH(6, "审核完成"),

    //裁剪计划作业详情
    TAILORING_DETAIL_STATUS_DELETE(-1, "删除"),
    TAILORING_DETAIL_STATUS_DEFAULT(1, "初始"),
    TAILORING_DETAIL_STATUS_START(2, "提交审核"),
    TAILORING_DETAILSTATUS_FINISH(3, "完成"),


    //任务
    TAILORING_TASK_STATUS_DELETE(-1, "删除"),
    TAILORING_TASK_STATUS_DEFAULT(1, "初始"),
    TAILORING_TASK_STATUS_START(2, "任务开始"),
    TAILORING_TASK_STATUS_SUBMIT(3, "提交审核"),
    TAILORING_TASK_STATUS_PASS(4, "审核通过"),
    TAILORING_TASK_STATUS_NOPASS(5, "审核没通过"),


    //审核
    TAILORING_EXAMINE_STATUS_DELETE(-1, "删除"),
    TAILORING_EXAMINE_STATUS_DEFAULT(1, "提交审核"),
    TAILORING_EXAMINE_STATUS_PASS(2, "审核通过"),
    TAILORING_EXAMINE_STATUS_NOPASS(3, "审核没通过"),


    //拉布结果状态
    SPREADING_STATUS_SUCCESS(1, "成功"),
    SPREADING_STATUS_PARTIAL_PLAN_COMPLETED(2, "部分计划完成"),
    SPREADING_STATUS_TAILORING_IS_TOO_BIG(3, "裁剪数量大于最大允许裁剪数量"),
    SPREADING_STATUS_TASK_SUBMITTED(3, "任务已经提交"),
    SPREADING_STATUS_LENGTH_EXCEEDED(3, "拉布长度超出理论长度太多"),
    SPREADING_STATUS_PLAN_COMPLETION(3, "计划完成"),
    SPREADING_STATUS_DIFFERENT_CLOTH_NUMBERS(3, "布匹号不一致"),


    //布头
    LEFT_STATUS_ACTUAL_LENGTH_LESS_THAN_THEORETICAL_LENGTH(1, "实际长度小于理论长度");


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
