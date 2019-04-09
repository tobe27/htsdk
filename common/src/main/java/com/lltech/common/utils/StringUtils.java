package com.lltech.common.utils;

import com.lltech.common.enums.DatePatternEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";
    /*
     * 正则表达式
     * 1. ^ 代表开始
     * 2. $ 代表结束
     * 3. ? 代表一位，等价于{0，1}
     * 4. * 代表多位，等价于{0，}
     * 5. + 代表匹配前面的至少一次
     * 6. []{m,n} 代表长度是m-n位
     * 7. []{m} 代表长度是m位
     */
    // 正则表达式.手机格式，必须1开头的11位电话号码
    public static final String PHONE_NUMBER = "^1[3|4|5|6|7|8|9]?[0-9]{9}$";
    // 邮箱格式，格式必须为example@example.com
    public static final String EMAIL = "^[a-zA-Z0-9_]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
    // 英文、数字、下划线
    public static final String ALPHABET_NUMERIC = "^[a-zA-Z0-9_]*$";
    // 英文、数字、_+-.@等非特殊字符，用于密码
    public static final String ALPHABET_NUMERIC_SYMBOL = "^[a-zA-Z0-9_+-.@]*$";
    // 整数或浮点数数字,包括小数
    public static final String  NUMERIC = "((^[1-9]+[0-9]*[.]?)|(^[0]+[.]))[0-9]*$";
    // 纯数字
    public static final String NUMBER = "^[0-9]*$";

    private StringUtils(){}

    /**
     * 创建指定数量的随机字符串
     * @param numberFlag 是否是数字
     * @param length 不小于2
     * @return 字符串
     */
    public static String createRandom(boolean numberFlag, int length) {
        if (length < 2) {
            throw new IllegalArgumentException("无效长度 {length=" + length + "},长度不小于2");
        }
        String retStr;
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase(" hello_world ") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获得当天是周几
     */
    public static String getWeekDay(){
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekDays[w];
    }


    /**
     * 校验是否为身份证
     * @param param 18位身份证号
     * @return boolean
     */
    public static boolean isNotIdNumber(String param) {
        return !isIdNumber(param);
    }

    /**
     * 校验是否为身份证
     *
     * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
     *
     *  1. 前1、2位数字表示：所在省份的代码；
     *  2. 第3、4位数字表示：所在城市的代码；
     *  3. 第5、6位数字表示：所在区县的代码；
     *  4. 第7~14位数字表示：出生年、月、日；
     *  5. 第15、16位数字表示：所在地的派出所的代码；
     *  6. 第17位数字表示性别：奇数表示男性，偶数表示女性；
     *  7. 第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示10。
     *
     * 第十八位数字(校验码)的计算方法为：
     *  1. 将前面的身份证号码17位数分别乘以不同的系数,从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     *  2. 将这17位数字和系数相乘的结果相加,用加出来和除以11，看余数是多少
     *  3. 余数只可能有 0 1 2 3 4 5 6 7 8 9 10 ,其分别对应的最后一位身份证的号码为 1 0 X 9 8 7 6 5 4 3 2
     *  4. 通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     *
     *  省、直辖市代码表：
     *     11 : 北京  12 : 天津  13 : 河北    14 : 山西  15 : 内蒙古
     *     21 : 辽宁  22 : 吉林  23 : 黑龙江
     *     31 : 上海  32 : 江苏  33 : 浙江   34 : 安徽  35 : 福建   36 : 江西  37 : 山东
     *     41 : 河南  42 : 湖北  43 : 湖南   44 : 广东  45 : 广西   46 : 海南
     *     50 : 重庆  51 : 四川  52 : 贵州   53 : 云南  54 : 西藏
     *     61 : 陕西  62 : 甘肃  63 : 青海   64 : 宁夏  65 : 新疆
     *     71 : 台湾
     *     81 : 香港  82 : 澳门
     *     91 : 国外
     *
     * @param param 18位身份证号
     * @return boolean
     */
    public static boolean isIdNumber(String param) {
        //  省、直辖市代码
        String[] cityCode = {"11", "12", "13", "14", "15",
                "21", "22", "23",
                "31", "32", "33", "34", "35", "36", "37",
                "41", "42", "43", "44", "45", "46",
                "50", "51", "52", "53", "54",
                "61", "62", "63", "64", "65",
                "71",
                "81", "82",
                "91"};

        List<String> cityList = new ArrayList<>(cityCode.length);
        Collections.addAll(cityList, cityCode);

        // 为空或者长度不符
        boolean isNot18Length = isBlank(param) || param.length() != 18;
        if (isNot18Length) {
            return false;
        }

        // 前17位为数字
        String front17 = param.substring(0, 17);
        boolean isNot17Number = !front17.matches(NUMBER);
        if (isNot17Number) {
            return false;
        }

        // 校验省份代码
        String provinceCode = param.substring(0, 2);
        if (!cityList.contains(provinceCode)) {
            return false;
        }

        // 校验出生日期
        String birthday = param.substring(6, 14);
        long birthday2Long;
        // 出生日期限定为19000101-现在
        long Long1900 = -2209017943000L;
        long now2Long = DateUtils.getMillisecond();
        try {
            birthday2Long = DateUtils.convertDate2Long(birthday, DatePatternEnum.DATE_TIME_SECOND);
        } catch (Exception e) {
            return false;
        }
        boolean isBirthday = birthday2Long > Long1900 && birthday2Long < now2Long;
        if (!isBirthday) {
            return false;
        }

        // 校验18位校验码
        return verifyPower(param);
    }

    /**
     * 校验18位校验码
     * @param param
     * @return
     */
    private static boolean verifyPower(String param) {
        // 身份证号前17位分别对应的加权数
        int[] power = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
        int sum17 = 0;
        String front17 = param.substring(0, 17);
        String last1 = param.substring(17, 18).toUpperCase();
        int[] number17Arr = convertString2IntArr(front17);
        for (int i = 0; i < power.length; i++) {
            sum17 += number17Arr[i] * power[i];
        }
        // 校验码，对11取余
        String checkCode = null;
        switch (sum17 % 11) {
            case 10:
                checkCode = "2";
                break;
            case 9:
                checkCode = "3";
                break;
            case 8:
                checkCode = "4";
                break;
            case 7:
                checkCode = "5";
                break;
            case 6:
                checkCode = "6";
                break;
            case 5:
                checkCode = "7";
                break;
            case 4:
                checkCode = "8";
                break;
            case 3:
                checkCode = "9";
                break;
            case 2:
                checkCode = "X";
                break;
            case 1:
                checkCode = "0";
                break;
            case 0:
                checkCode = "1";
                break;
            default:
        }

        // 如果校验码相同
        return last1.equals(checkCode);
    }

    /**
     * 身份证前17位转换为数字数组
     * @param front17 身份证前17位
     * @return 数组
     */
    private static int[] convertString2IntArr(String front17) {
        char[] strArr = front17.toCharArray();
        int[] intArr = new int[strArr.length];
        int k = 0;
        for (char temp : strArr) {
            intArr[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return intArr;
    }
}
