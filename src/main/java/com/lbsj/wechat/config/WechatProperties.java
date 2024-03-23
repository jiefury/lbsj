package com.lbsj.wechat.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(WechatProperties.PREFIX)
@Data
@Component
public class WechatProperties {
    public static final String PREFIX = "wechat";

    private String url = "https://api.weixin.qq.com";

    private String appId;

    private String secret;

    private String vToken;

}
