package com.cfox.openshare.platform.weixin.http;

import com.cfox.openshare.http.ReqBuild;

import java.util.HashMap;
import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform.weixin.http
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class WXRequest {

    /**
     * 创建请求 AccessToken 的请求url ,请求参数
     * @param appId  微信 APPID
     * @param secret 微信 secret key
     * @param code 登录时返回的 code
     * @return
     */
    public static ReqBuild getAccessTokenBuild(String appId,String secret,String code ){
        ReqBuild build = new ReqBuild();
        build.reqType = ReqBuild.GET;
        build.reqUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        Map<String,String> params = new HashMap<>();
        params.put("appid",appId);
        params.put("secret",secret);
        params.put("code",code);
        params.put("grant_type","authorization_code");
        build.params = params;
        return build;
    }


    /**
     * 创建请求 用户信息 的请求url ,请求参数
     * @param accessToken
     * @param openId
     * @return
     */
    public static ReqBuild getUserInfoBuild(String accessToken,String openId ){
        ReqBuild build = new ReqBuild();
        build.reqType = ReqBuild.GET;
        build.reqUrl = "https://api.weixin.qq.com/sns/userinfo";
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accessToken);
        params.put("openid",openId);
        build.params = params;
        return build;
    }
}
