package com.xiruo.medbid.util;

import java.math.BigDecimal;

public class DecimalUtil {

    //金额的精度，默认为保留2位小数
    private static final Integer SCALE = 2;

    /**
     * 四舍五入，默认保留2位小数
     * @param value
     * @return
     */
    public static BigDecimal round(BigDecimal value) {
        BigDecimal result = value.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    /**
     * 小数位直接截断，保留scale位小数
     * @param value
     * @param scale
     * @return
     */
    public static BigDecimal round(BigDecimal value, int scale) {
        BigDecimal result = value.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    /**
     * 保留scale位小数
     * @param value
     * @param scale 保留scale位小数
     * @param round 小数位截断方式：RoundingMode.DOWN
     * @return
     */
    public static BigDecimal round(BigDecimal value, int scale, int round) {
        BigDecimal result = value.setScale(scale, round);
        return result;
    }

    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal(123.0076);
        BigDecimal availableLimit = new BigDecimal(123.008);

        System.out.println(amount);
        System.out.println(availableLimit);
        System.out.println(DecimalUtil.round(amount, 3));
        System.out.println(DecimalUtil.round(availableLimit, 3));

        int vl = DecimalUtil.round(amount, 3).compareTo(DecimalUtil.round(availableLimit, 3));
        System.out.println(vl);
    }
}
