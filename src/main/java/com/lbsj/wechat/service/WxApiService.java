package com.lbsj.wechat.service;


import com.lbsj.wechat.model.AccessTokenModel;
import com.lbsj.wechat.model.JsSignVO;
import com.lbsj.wechat.model.WxVerificationModel;

public interface WxApiService {
    /**
     * 获取jssdk 签名
     *
     * @param page
     * @return
     */
    JsSignVO getJsSign(String page);

    AccessTokenModel getAccessToken();

    AccessTokenModel getJsapiTicket();
    String wxVerification(WxVerificationModel wxVerificationModel);
}
