package com.zhenqi.baseutil.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author mtj
 * @time 2019/6/22 2019 06
 * @des
 */
public class MathUtil {

    public static final String FORMAT462 = "#0.00";
    public static final String FORMAT461 = "#0.0";
    public static final String FORMAT460 = "#0";

    public static final String FORMAT452 = "#0.00";
    public static final String FORMAT451 = "#0.0";
    public static final String FORMAT450 = "#0";

    /**
     * 四舍五入
     *
     * @param value
     * @return
     */
    public static String format_4_5(float value, String format) {
        DecimalFormat mDecimalFormat = new DecimalFormat(format);
        mDecimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return mDecimalFormat.format(new BigDecimal(String.valueOf(value)));
    }

    /**
     * 四舍五入
     *
     * @param value
     * @return
     */
    public static String format_4_5(double value, String format) {
        DecimalFormat mDecimalFormat = new DecimalFormat(format);
        mDecimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return mDecimalFormat.format(new BigDecimal(String.valueOf(value)));
    }

    /**
     * 四舍六入
     *
     * @param value
     * @return
     */
    public static String format_4_6(float value, String format) {
        NumberFormat numberFormat = new DecimalFormat(format);
        return numberFormat.format(new BigDecimal(String.valueOf(value)));
    }
    /**
     * 四舍六入
     *
     * @param value
     * @return
     */
    public static String format_4_6(double value, String format) {
        NumberFormat numberFormat = new DecimalFormat(format);
        return numberFormat.format(new BigDecimal(String.valueOf(value)));
    }


}
