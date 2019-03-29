package com.lltech.common.enums;

/**
 * 时间格式枚举类-保证时间参数限定
 * @author Created by L.C.Y on 2018-11-6
 */
public enum DatePatternEnum {
    DATE("yyyyMMdd"),
    DATE_("yyyy-MM-dd"),
    DATE_TIME_SECOND("yyyyMMdd HH:mm:ss"),
    DATE_TIME_SECOND_("yyyy-MM-dd HH:mm:ss"),
    DATE_TIME_MILLIS("yyyyMMdd HH:mm:ss.SSS"),
    DATE_TIME_MILLIS_("yyyy-MM-dd HH:mm:ss.SSS");

    private String datePattern;

    DatePatternEnum(String datePattern) {
        this.datePattern = datePattern;
    }

    public String getDatePattern() {
        return datePattern;
    }
}
