package com.cfox.openshare.platform.weixin.listener;

import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.IBackListener;
import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.platform.weixin.WXConfig;
import com.cfox.openshare.parse.IParse;
import com.cfox.openshare.utils.OSLog;

import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform.weixin.listener
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class WXCallbackListener {

    private static final String TAG = "WXCallbackListener";

    public static final int LOGIN       = 0x001;
    public static final int USER_INFO   = 0x002;

    public int sign = 0;
    public IBackListener mListener;
    public IParse mParse;

    public WXCallbackListener() {}

    public void onComplete(String str){

        Map<String ,String > infoBuff = mParse.parseToMap(str);

        if (mListener != null)
            mListener.onComplete(SHARE_MEDIA.WEIXIN, infoBuff);

        WXConfig wxConfig = (WXConfig) PlatformConfig.configs.get(SHARE_MEDIA.WEIXIN);
        if (wxConfig == null){
            OSLog.e(TAG,"WXConfig is null , Please use the class PlatformConfig Settings");
            return;
        }

        if (sign == LOGIN){
            wxConfig.authInfo = infoBuff;
            return;
        }

        if (sign == USER_INFO){
            wxConfig.userInfo = infoBuff;
        }
    }

    public void onError(int errorCode,String errorMessage){
        mListener.onError(SHARE_MEDIA.WEIXIN,errorCode,errorMessage);
    }

    public void onCancel(){
        ((IAuthListener)mListener).onCancel(SHARE_MEDIA.WEIXIN);
    }
}
