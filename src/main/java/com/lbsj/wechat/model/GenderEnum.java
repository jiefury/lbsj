package com.lbsj.wechat.model;

public enum GenderEnum {
    UNKNOWN(0,"未知"),
    MALE(1,"男"),
    FEMALE(2,"女");

    private int code;
    private String name;

    GenderEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getName(int code) {
        for (GenderEnum c : GenderEnum.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }

    public static int getCode(String name) {
        for (GenderEnum c : GenderEnum.values()) {
            if (c.getName() == name) {
                return c.code;
            }
        }
        return -1;
    }
}
