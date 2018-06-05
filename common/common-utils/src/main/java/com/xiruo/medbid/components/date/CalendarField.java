package com.xiruo.medbid.components.date;

import java.util.Calendar;

/**
 * @title：
 * @describe：
 * @copyright：Copyright (c) 1997-2017 coracle, Inc.
 * @company：圆舟科技
 * @author：taok
 * @date：2017-11-14 9:38
 * @version：1.0
 */
public enum CalendarField {
    YEAR(Calendar.YEAR, "年"), MONTH(Calendar.MONTH, "月"), DATE(Calendar.DATE, "日"),
    WEEK(Calendar.WEEK_OF_YEAR, "周");
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    CalendarField(int index, String name) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (CalendarField c : CalendarField.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
