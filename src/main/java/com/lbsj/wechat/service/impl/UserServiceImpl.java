package com.lbsj.wechat.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.lbsj.wechat.config.WechatProperties;
import com.lbsj.wechat.model.CodeTokenVO;
import com.lbsj.wechat.model.UserWxVO;
import com.lbsj.wechat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WechatProperties wechatProperties;

    @Override
    public CodeTokenVO getOpenid(String code) {
        CodeTokenVO codeTokenVO = codeDeputed(code);
        return codeTokenVO;
    }

    @Override
    public UserWxVO getUserWx(String code) {
        CodeTokenVO codeTokenVO = codeDeputed(code);

        UserWxVO userWxVO = userInfo(codeTokenVO.getOpenid(), codeTokenVO.getAccessToken());
        return userWxVO;
    }

    @Override
    public UserWxVO getUserWx(String openid, String accessToken) {
        UserWxVO userWxVO = userInfo(openid, accessToken);
        return userWxVO;
    }

    /**
     * 通过code换取网页授权access_token及openid
     *
     * @param code
     * @return
     */
    private CodeTokenVO codeDeputed(String code) {
        String url = this.wechatProperties.getUrl() + "/sns/oauth2/access_token";
        String APPID = this.wechatProperties.getAppId();
        String appsecret = this.wechatProperties.getSecret();

        String httpUrl = url + "?appid=" + APPID + "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
        String jsonStr = restTemplate.getForObject(httpUrl, String.class);

        CodeTokenVO codeTokenVO = JSONObject.parseObject(jsonStr, CodeTokenVO.class);
        return codeTokenVO;
    }

    /**
     * 通过access_token和openid拉取用户信息
     *
     * @param openid
     * @param accessToken
     * @return
     */
    private UserWxVO userInfo(String openid, String accessToken) {
        String url = this.wechatProperties.getUrl() + "/sns/userinfo";

        String httpUrl = url + "?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";
        String jsonStr = restTemplate.getForObject(httpUrl, String.class);
        UserWxVO userWxVO = JSONObject.parseObject(jsonStr, UserWxVO.class);
        return userWxVO;
    }
}
