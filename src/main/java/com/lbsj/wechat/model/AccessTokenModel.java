package com.lbsj.wechat.model;

import lombok.Data;

@Data
public class AccessTokenModel {

    /**
     * 获取到的凭证
     */
    private String accessToken;

    /**
     * 公众号用于调用微信JS接口的临时票据
     */
    private String ticket;


    /**
     * 凭证有效时间，单位：秒
     */
    private Long expiresIn;
}
