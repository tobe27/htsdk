package com.lltech.common.utils;


import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 用于WEB层标准输出格式
 * code
 * message
 * count
 * data
 * @author Created by L.C.Y on 2018-11-20
 */
public class ResultBean extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 7112950860836859973L;
    private static final String CODE = "code";
    private static final String MESSAGE = "message";
    private static final String COUNT = "count";
    private static final String DATA = "data";

    /**
     * 请求成功
     * @return
     */
    public ResultBean ok() {
        this.put(CODE, HttpStatus.OK.value());
        this.put(MESSAGE, HttpStatus.OK);
        return this;
    }

    /**
     * 请求成功并返回数据
     * 如果是列表则判断是否为空
     * @param data 返回的数据
     * @return
     */
    public ResultBean ok(Object data) {
        boolean isEmptyList = (data instanceof List<?>) && ((List<?>) data).isEmpty();
        if (data == null || isEmptyList) {
            this.put(CODE, HttpStatus.NO_CONTENT.value());
            this.put(MESSAGE, HttpStatus.NO_CONTENT);
            return this;
        }
        this.put(CODE, HttpStatus.OK.value());
        this.put(MESSAGE, HttpStatus.OK);
        this.put(DATA, data);
        return this;
    }

    /**
     * 请求成功并返回数据
     * @param count 数据列数
     * @param data 返回的数据
     * @return
     */
    public ResultBean ok(long count, List data) {
        if (data == null || data.isEmpty()) {
            this.put(CODE, HttpStatus.NO_CONTENT.value());
            this.put(MESSAGE, HttpStatus.NO_CONTENT);
            return this;
        }
        this.put(CODE, HttpStatus.OK.value());
        this.put(MESSAGE, HttpStatus.OK);
        this.put(COUNT, count);
        this.put(DATA, data);
        return this;
    }


    /**
     * 请求失败，并返回失败信息
     * @param message 失败信息
     * @return
     */
    public ResultBean error(String message) {
        return error(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * 请求失败，并返回失败信息
     * @param code 返回的HttpStatus
     * @param message
     * @return
     */
    public ResultBean error(HttpStatus code, String message) {
        this.put(CODE, code.value());
        this.put(MESSAGE, message);
        return this;
    }

    @Override
    public ResultBean put(String key, Object data) {
        super.put(key, data);
        return this;
    }

}
