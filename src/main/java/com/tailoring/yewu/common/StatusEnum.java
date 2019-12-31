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
    TAILORING_EXAMINE_STATUS_NOPASS(3, "审核没通过");


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
