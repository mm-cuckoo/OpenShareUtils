package com.cfox.openshare.open.sina.listener;

import android.os.Bundle;

import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.open.sina.SinaConfig;
import com.cfox.openshare.utils.OSLog;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

import java.util.HashMap;
import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open.sina.listener
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class SinaAuthListener implements WeiboAuthListener {
    private static final String TAG = "SinaAuthListener";

    public IAuthListener mAuthListener;
    @Override
    public void onComplete(Bundle bundle) {
        SinaConfig sinaConfig = (SinaConfig) PlatformConfig.configs.get(SHARE_MEDIA.SIAN);
        if (sinaConfig == null){
            OSLog.e(TAG,"SinaConfig is null , Please use the class PlatformConfig Settings");
            return;
        }
        Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
        sinaConfig.mAccessToken = mAccessToken;

        Map<String,String> accessMap = new HashMap<>();
        accessMap.put("accessToken",mAccessToken.getToken());
        accessMap.put("uid",mAccessToken.getUid());
        accessMap.put("phoneNum",mAccessToken.getPhoneNum());
        accessMap.put("refreshToken",mAccessToken.getRefreshToken());
        accessMap.put("expiresTime",String.valueOf(mAccessToken.getExpiresTime()));
        mAuthListener.onComplete(SHARE_MEDIA.SIAN,accessMap);
    }

    @Override
    public void onWeiboException(WeiboException e) {
        mAuthListener.onError(SHARE_MEDIA.SIAN,-1,e.getMessage());
    }

    @Override
    public void onCancel() {
        mAuthListener.onCancel(SHARE_MEDIA.SIAN);
    }
}
