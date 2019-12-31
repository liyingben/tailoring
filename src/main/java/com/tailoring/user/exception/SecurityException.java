package com.tailoring.user.exception;

import com.tailoring.user.common.BaseException;
import com.tailoring.user.common.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 全局异常
 * </p>
 *
 * @package: com.tailoring.user.exception
 * @description: 全局异常
 * @author: ben
 * @date: Created in 2018-12-10 17:24
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SecurityException extends BaseException {
    public SecurityException(Status status) {
        super(status);
    }

    public SecurityException(Status status, Object data) {
        super(status, data);
    }

    public SecurityException(Integer code, String message) {
        super(code, message);
    }

    public SecurityException(Integer code, String message, Object data) {
        super(code, message, data);
    }
}
