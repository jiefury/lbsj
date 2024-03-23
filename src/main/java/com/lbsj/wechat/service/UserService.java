package com.lbsj.wechat.service;

import com.lbsj.wechat.model.CodeTokenVO;
import com.lbsj.wechat.model.UserWxVO;

public interface UserService {

    /**
     * 获取Openid
     *
     * @param code 前端获取的code
     * @return
     */
    CodeTokenVO getOpenid(String code);

    /**
     * @param code 前端获取的code
     * @return
     */
    UserWxVO getUserWx(String code);

    /**
     * @param openid      用户唯一标识
     * @param accessToken 用户调用信息验证的token
     * @return
     */
    UserWxVO getUserWx(String openid, String accessToken);
}
