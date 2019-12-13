package com.tailoring.yewu.common;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Data
public class ActionResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 结果状态码
     */
    private int code = ResultType.OK.getCode();
    /**
     * 结果消息
     */
    private String message = ResultType.OK.getDesc();
    /**
     * 结果数据
     */
    private T data;

	public ActionResult() {
        super();
    }

    private void packResult(ResultType resultType, String message) {
        this.code = resultType.getCode();
        if (!StringUtils.isEmpty(message)) {
            this.message = message;
        } else {
            this.message = resultType.getDesc();
        }
    }

    public ActionResult(int code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ActionResult(T data) {
	    this.data = data;
	}

    public ActionResult(ResultType resultType) {
        packResult(resultType, "");
    }

    public ActionResult(ResultType resultType, T data) {
        packResult(resultType, "");
        this.data = data;
    }

    public ActionResult(ResultType resultType, String message) {
        packResult(resultType, message);
    }

    public ActionResult(ResultType resultType, String message, T data) {
        packResult(resultType, message);
        this.data = data;
    }

}
