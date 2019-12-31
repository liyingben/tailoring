package com.tailoring.user.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 通用分页参数返回
 * </p>
 *
 * @package: com.tailoring.user.common
 * @description: 通用分页参数返回
 * @author: ben
 * @date: Created in 2018-12-11 20:26
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: ben
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 3420391142991247367L;

    /**
     * 当前页数据
     */
    private List<T> rows;

    /**
     * 总条数
     */
    private Long total;

    public static <T> PageResult of(List<T> rows, Long total) {
        return new PageResult<>(rows, total);
    }
}
