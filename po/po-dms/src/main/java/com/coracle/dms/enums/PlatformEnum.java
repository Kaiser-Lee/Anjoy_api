package com.coracle.dms.enums;

/**
 * @Description：
 * @Author：taok
 * @Version：
 * @Date：2018/1/30 11:39
 */
public enum PlatformEnum {
    YANGGUANG(100, "阳光平台"),
    AJJOY(101, "安井平台");

    private int type;
    private String desc;

    private PlatformEnum(int type, String desc) {
        this.type =type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return this.desc;
    }

    public static String getDesc(int type) {
        for (NewsTypeEnum e : NewsTypeEnum.values()) {
            if (e.getType() == type) {
                return e.getDesc();
            }
        }
        return null;
    }
}
