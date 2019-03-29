package com.lltech.common.utils;

import com.lltech.common.enums.DatePatternEnum;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 时间转换工具
 * @author Created by L.C.Y on 2018-11-6
 */
public class DateConvertUtil {

    private DateConvertUtil() {}

    /**
     * 毫秒时间戳
     * @return 毫秒时间戳
     */
    public static long getMillisecond() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 秒时间戳
     * @return 秒时间戳
     */
    public static long getSecond() {
        return Instant.now().getEpochSecond();
    }

    /**
     * 将毫秒时间戳转换成字符串,默认格式为yyyy-MM-dd
     * @param timeStamp 时间戳
     * @return 时间字符串
     */
    public static String convert2String(Long timeStamp) {
        return convert2String(timeStamp, DatePatternEnum.DATE_);
    }

    /**
     * 将毫秒时间戳转换成限定格式的字符串
     * @param timeStamp 时间戳
     * @param datePattenEnum 时间格式枚举类
     * @return 时间字符串
     */
    public static String convert2String(long timeStamp, DatePatternEnum datePattenEnum) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattenEnum.getDatePattern());
        return dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault()));
    }

    /**
     * 将DateTime字符串时间转换成毫秒时间戳
     * @param dateTime 时间格式必须与枚举类相同
     * @param datePattenEnum 时间格式枚举类,只可选择如下枚举
     *                        DATE_TIME_SECOND("yyyyMMdd HH:mm:ss"),
     *                        DATE_TIME_SECOND_("yyyy-MM-dd HH:mm:ss"),
     *                        DATE_TIME_MILLIS("yyyyMMdd HH:mm:ss.SSS"),
     *                        DATE_TIME_MILLIS_("yyyy-MM-dd HH:mm:ss.SSS");
     * @return 时间戳
     */
    public static Long convertDateTime2Long(String dateTime, DatePatternEnum datePattenEnum) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattenEnum.getDatePattern());
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        return LocalDateTime.from(localDateTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将Date字符串时间转换成毫秒时间戳
     * @param date 时间格式必须与枚举类相同
     * @param datePattenEnum 时间格式枚举类,只可选择如下两种
     *                       DATE_TIME_SECOND("yyyyMMdd HH:mm:ss"),
     *                       DATE_TIME_SECOND_("yyyy-MM-dd HH:mm:ss")
     * @return 时间戳
     */
    public static Long convertDate2Long(String date, DatePatternEnum datePattenEnum) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattenEnum.getDatePattern());
        date = date + " 00:00:00";
        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
        return LocalDateTime.from(localDateTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }


}
