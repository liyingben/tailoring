package com.tailoring.yewu.mapper;


import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author sunzhiqiang
 * @date 2018/11/13
 * @desc mapstruct基础接口
 */
public interface BaseEntityMapper<S, T> {

    T s2T(S s);

    S t2S(T t);

    default List<T> ss2Ts(List<S> sList) {

        if (sList == null || sList.size() == 0) {
            return Lists.newArrayList();
        }

        List<T> tList = Lists.newArrayList();
        for (S s : sList) {
            tList.add(s2T(s));
        }

        return tList;
    }

    default List<S> ts2Ss(List<T> tList) {

        if (tList == null || tList.size() == 0) {
            return Lists.newArrayList();
        }

        List<S> sList = Lists.newArrayList();
        for (T t : tList) {
            sList.add(t2S(t));
        }

        return sList;
    }
}
