package com.lltech.common.utils;


import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 用于WEB层标准输出格式
 * code 200- 请求成功，并返回数据，204- 请求成功，不返回数据
 *      300- 未登录或登录失效，重定向到登录页面
 *      400- 请求失败，401- 没有访问权限，403- 被禁止访问
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
     * 请求成功, 但不返回数据
     * @return code- 204, msg- No Content
     */
    public ResultBean ok() {
        this.put(CODE, HttpStatus.NO_CONTENT.value());
        this.put(MESSAGE, HttpStatus.NO_CONTENT);
        return this;
    }

    /**
     * 请求成功并返回数据
     * @param data 空数据：code- 204, msg- No Content
     *             非空数据：code- 200, msg- OK，data- data
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
     * @param count 数据总列数
     * @param data 空数据：code- 204, msg- No Content
     *             非空数据：code- 200, msg- OK，count- 总列数，data- data
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
     * @return code-400, msg- 请求错误信息
     */
    public ResultBean error(String message) {
        return error(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * 请求失败，并返回失败信息
     * @param code 返回的HttpStatus
     * @param message 返回的错误信息
     * @return code- 401, msg- 没有访问权限
     *        code- 403, msg- 被禁止访问
     */
    public ResultBean error(HttpStatus code, String message) {
        this.put(CODE, code.value());
        this.put(MESSAGE, message);
        return this;
    }

    /**
     * 添加额外信息
     * @param key key
     * @param data data
     * @return key- data
     */
    @Override
    public ResultBean put(String key, Object data) {
        super.put(key, data);
        return this;
    }

}
