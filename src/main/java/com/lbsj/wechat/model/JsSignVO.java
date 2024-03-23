package com.lbsj.wechat.model;

public class JsSignVO {

    /**
     * 随机字符串
     */
    private String noncestr;

    /**
     * 签名
     */
    private String signature;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * appid
     */
    private String appId;

    public JsSignVO(String noncestr, String jsapiTicket, Long timestamp) {
        this.noncestr = noncestr;
        this.signature = jsapiTicket;
        this.timestamp = timestamp;
    }

    public JsSignVO(String noncestr, String jsapiTicket, Long timestamp, String appId) {
        this.noncestr = noncestr;
        this.signature = jsapiTicket;
        this.timestamp = timestamp;
        this.appId = appId;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
