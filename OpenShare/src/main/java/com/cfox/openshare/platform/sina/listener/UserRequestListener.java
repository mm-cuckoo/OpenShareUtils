package com.cfox.openshare.platform.sina.listener;

import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.IUserListener;
import com.cfox.openshare.platform.sina.SinaConfig;
import com.cfox.openshare.parse.IParse;
import com.cfox.openshare.utils.OSLog;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform.sina.listener
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class UserRequestListener implements RequestListener {

    private static final String TAG = "UserRequestListener";

    public IUserListener mListener;
    public IParse mParse;

    public UserRequestListener() {

    }

    public UserRequestListener(IUserListener mListener, IParse mParse) {
        this.mListener = mListener;
        this.mParse = mParse;
    }

    @Override
    public void onComplete(String s) {

        Map<String ,String > infoBuff = mParse.parseToMap(s);

        if (mListener != null)
            mListener.onComplete(SHARE_MEDIA.SIAN, infoBuff);

        SinaConfig sinaConfig = (SinaConfig) PlatformConfig.configs.get(SHARE_MEDIA.SIAN);
        if (sinaConfig == null){
            OSLog.e(TAG,"sinaConfig is null , Please use the class PlatformConfig Settings");
            return;
        }
        sinaConfig.userInfo = infoBuff;
    }

    @Override
    public void onWeiboException(WeiboException e) {
        if (mListener != null)
            mListener.onError(SHARE_MEDIA.SIAN,-1,e.getMessage());
    }
}
