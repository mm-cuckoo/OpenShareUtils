package com.cfox.openshare.platform.sina;

import com.cfox.openshare.AbsConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform.sina
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class SinaConfig extends AbsConfig {

    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    public Oauth2AccessToken mAccessToken;


    @Override
    public SHARE_MEDIA getName() {
        return SHARE_MEDIA.SIAN;
    }

    @Override
    public boolean isConfigured() {
        return !(appId == null || appSecret == null);
    }

    @Override
    public boolean isAuthrized() {
        return mAccessToken != null;
    }
}
