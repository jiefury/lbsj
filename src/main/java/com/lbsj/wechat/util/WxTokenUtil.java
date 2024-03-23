package com.lbsj.wechat.util;


import com.lbsj.wechat.model.AccessTokenModel;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class WxTokenUtil {

    private static final Map<String, AccessTokenModel> TOKEN_MAP = new ConcurrentHashMap<>();

    public static void delete(String key) {
        TOKEN_MAP.remove(key);
    }

    public static void set(String key, AccessTokenModel accessTokenModel) {
        Long expiresIn = new Date().getTime() / 1000 + accessTokenModel.getExpiresIn();
        accessTokenModel.setExpiresIn(expiresIn);
        TOKEN_MAP.put(key, accessTokenModel);
    }

    public static AccessTokenModel get(String key) {
        AccessTokenModel accessTokenModel = TOKEN_MAP.get(key);
        if (null == accessTokenModel) {
            return null;
        }

        Long expiresIn = new Date().getTime() / 1000;
        if (expiresIn >= accessTokenModel.getExpiresIn()) {
            delete(key);
            return null;
        }
        return accessTokenModel;
    }
}
