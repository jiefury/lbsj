package com.lbsj.common.handler;

/**
 * @author itranlin
 */
public class StringHelper {
    public static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
