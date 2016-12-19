package com.cfox.openshare.open.qq.listener;

import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.open.qq.QQConfig;
import com.cfox.openshare.parse.IParse;
import com.cfox.openshare.utils.OSLog;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open.qq.listener
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class QOpenListener implements IUiListener {

    private static final String TAG = "QOpenListener";

    public static final int LOGIN = 0x001;
    public static final int USER_INFO = 0x002;

    public int sign = 0;
    public IAuthListener mListener;
    public IParse mParse;

    public QOpenListener() {
    }

    @Override
    public void onComplete(Object o) {

        Map<String ,String > authInfoBuff = mParse.parseToMap(o.toString());

        if (mListener != null)
            mListener.onComplete(SHARE_MEDIA.QQ, authInfoBuff);

        if (sign == LOGIN){
            QQConfig qqConfig = (QQConfig) PlatformConfig.configs.get(SHARE_MEDIA.QQ);
            if (qqConfig == null){
                OSLog.e(TAG,"QQConfig is null , Please use the class PlatformConfig Settings");
                return;
            }
            qqConfig.authInfo = authInfoBuff;
        }
    }

    @Override
    public void onError(UiError uiError) {
        if (mListener != null)
            mListener.onError(SHARE_MEDIA.QQ, uiError.errorCode, uiError.errorMessage);
    }

    @Override
    public void onCancel() {
        if (mListener != null)
            mListener.onCancel(SHARE_MEDIA.QQ);
    }
}
