package com.lbsj.wechat;


import com.lbsj.wechat.model.AccessTokenModel;
import com.lbsj.wechat.model.JsSignVO;
import com.lbsj.wechat.model.UserWxVO;
import com.lbsj.wechat.model.WxVerificationModel;
import com.lbsj.wechat.service.UserService;
import com.lbsj.wechat.service.WxApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dome")
public class DomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private WxApiService wxApiService;

    @GetMapping("getOpenid")
    public String getOpenid(String code) {
        System.out.println(code);
        String res = userService.getOpenid(code).getOpenid();
        System.out.println(res);
        return res;
    }

    @GetMapping("getUser")
    public UserWxVO getUser(String code) {
        UserWxVO userWxVO = userService.getUserWx(code);

        return userWxVO;
    }

    @GetMapping("getVerification")
    public String getVerification(@ModelAttribute WxVerificationModel wxVerificationModel) {
        String res = wxApiService.wxVerification(wxVerificationModel);
        System.out.println(res);

        return res;
    }

    @GetMapping("getJsSign")
    public JsSignVO getJsSign(String url) {
        JsSignVO jsSignVO = wxApiService.getJsSign(url);
        return jsSignVO;
    }

    @GetMapping("/token")
    public AccessTokenModel getToken() {
        AccessTokenModel accessToken = wxApiService.getAccessToken();
        return accessToken;
    }

    @GetMapping("/ticket")
    public AccessTokenModel getJsapiTicket() {
        AccessTokenModel accessToken = wxApiService.getJsapiTicket();
        return accessToken;
    }
}
