package com.cfox.openshare.open.sina.listener;

import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.open.sina.SinaConfig;
import com.cfox.openshare.parse.IParse;
import com.cfox.openshare.utils.OSLog;

import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open.sina.listener
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class SinaCallBackListener {

    private static final String TAG = "SinaCallBackListener";

    public static final int LOGIN       = 0x001;
    public static final int USER_INFO   = 0x002;

    public int sign = 0;
    public IAuthListener mListener;
    public IParse mParse;

    public void onComplete(String str){

        Map<String ,String > infoBuff = mParse.parseToMap(str);

        if (mListener != null)
            mListener.onComplete(SHARE_MEDIA.SIAN, infoBuff);

        SinaConfig sinaConfig = (SinaConfig) PlatformConfig.configs.get(SHARE_MEDIA.SIAN);
        if (sinaConfig == null){
            OSLog.e(TAG,"sinaConfig is null , Please use the class PlatformConfig Settings");
            return;
        }

        if (sign == LOGIN){
            sinaConfig.authInfo = infoBuff;
            return;
        }

        if (sign == USER_INFO){
            sinaConfig.userInfo = infoBuff;
        }
    }

    public void onError(int errorCode,String errorMessage){
        mListener.onError(SHARE_MEDIA.WEIXIN,errorCode,errorMessage);
    }

    public void onCancel(){
        mListener.onCancel(SHARE_MEDIA.WEIXIN);
    }

}
