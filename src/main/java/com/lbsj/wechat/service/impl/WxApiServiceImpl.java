package com.lbsj.wechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lbsj.wechat.config.WechatProperties;
import com.lbsj.wechat.model.AccessTokenModel;
import com.lbsj.wechat.model.JsSignVO;
import com.lbsj.wechat.model.WxVerificationModel;
import com.lbsj.wechat.service.WxApiService;
import com.lbsj.wechat.util.WxTokenUtil;
import com.lbsj.wechat.util.WxVerificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WxApiServiceImpl implements WxApiService {
    @Autowired
    private WechatProperties wechatProperties;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String wxVerification(WxVerificationModel wxVerificationModel) {
        boolean checkSignature = WxVerificationUtil
                .checkSignature(wxVerificationModel.getSignature(),
                        wxVerificationModel.getTimestamp(),
                        wxVerificationModel.getNonce(), wechatProperties.getVToken());
        if (checkSignature) {
            return wxVerificationModel.getEchostr();
        }
        return null;
    }

    @Override
    public JsSignVO getJsSign(String page) {
        AccessTokenModel jsapiTicket = getJsapiTicket();

        JsSignVO jsSignVO = WxVerificationUtil.signatureSha1(jsapiTicket.getTicket(), page);
        String appId = this.wechatProperties.getAppId();
        jsSignVO.setAppId(appId);
        return jsSignVO;
    }

    @Override
    public AccessTokenModel getAccessToken() {
        String url = this.wechatProperties.getUrl() + "/cgi-bin/token";
        String appId = this.wechatProperties.getAppId();
        String appsecret = this.wechatProperties.getSecret();

        AccessTokenModel accessTokenModel = WxTokenUtil.get(appId + "_token");
        if (null != accessTokenModel) {
            return accessTokenModel;
        }
        String httpUrl = url + "?grant_type=client_credential&appid=" + appId + "&secret=" + appsecret;
        String jsonStr = restTemplate.getForObject(httpUrl, String.class);

        accessTokenModel = JSONObject.parseObject(jsonStr, AccessTokenModel.class);

        WxTokenUtil.set(appId + "_token", accessTokenModel);
        return accessTokenModel;
    }

    @Override
    public AccessTokenModel getJsapiTicket() {
        String url = this.wechatProperties.getUrl() + "/cgi-bin/ticket/getticket";
        String appid = this.wechatProperties.getAppId();

        AccessTokenModel jsapiTicket = WxTokenUtil.get(appid + "_ticket");
        if (null != jsapiTicket) {
            return jsapiTicket;
        }

        AccessTokenModel accessTokenModel = getAccessToken();

        String httpUrl = url + "?access_token=" + accessTokenModel.getAccessToken() + "&type=jsapi";
        String jsonStr = restTemplate.getForObject(httpUrl, String.class);
        jsapiTicket = JSONObject.parseObject(jsonStr, AccessTokenModel.class);

        WxTokenUtil.set(appid + "_ticket", jsapiTicket);
        return jsapiTicket;
    }


}
