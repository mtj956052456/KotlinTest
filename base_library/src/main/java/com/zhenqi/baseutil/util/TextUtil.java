package com.zhenqi.baseutil.util;

/**
 * @author 孟腾蛟
 * @time 2018/3/25 2018 03
 * @des
 */

public class TextUtil {

    /**
     * 将 null 转为 ""
     *
     * @param text
     * @return
     */
    public static String nullToStr(String text) {
        if (text == null) {
            return "";
        } else if ("null".equals(text)) {
            return "";
        }
        return text;
    }

    /**
     * 将 null 转为 "-"
     *
     * @param text
     * @return
     */
    public static String nullToStr_(String text) {
        if (text == null) {
            return "-";
        } else if ("null".equals(text)) {
            return "-";
        } else if ("".equals(text)) {
            return "-";
        }
        return text;
    }
    /**
     * 将 null 转为 "0"
     *
     * @param text
     * @return
     */
    public static String nullToStr_0(String text) {
        if (text == null) {
            return "0";
        } else if ("null".equals(text)) {
            return "0";
        } else if ("".equals(text)) {
            return "0";
        }
        return text;
    }
}
