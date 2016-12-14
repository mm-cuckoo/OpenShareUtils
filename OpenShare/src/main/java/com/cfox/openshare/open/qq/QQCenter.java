package com.cfox.openshare.open.qq;

import android.app.Activity;
import android.content.Context;

import com.cfox.openshare.OpenShareAPI;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.AuthListener;
import com.cfox.openshare.open.ICenter;
import com.cfox.openshare.open.qq.listener.UiListener;
import com.cfox.openshare.utils.OSLog;
import com.tencent.tauth.Tencent;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open.qq
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class QQCenter implements ICenter {

    private Context mContext;
    private Tencent mTencent;

    private QQConfig mQQConfig;

    public QQCenter(Context context){
        this.mContext = context.getApplicationContext();
        mQQConfig = (QQConfig) OpenShareAPI.configs.get(SHARE_MEDIA.QQ);
        if (mQQConfig == null){
            OSLog.e("openshare","place set appid and appSecret");
            return;
        }
        mTencent = Tencent.createInstance(mQQConfig.appId, mContext);
    }

    /**
     * 登录QQ
     */
    @Override
    public void login(AuthListener listener){
        if (!mTencent.isSessionValid()) {
            mTencent.login((Activity) mContext, "all", new UiListener(UiListener.LOGIN,listener));
        }else {
            OSLog.e("openshare","place set appid and appSecret");
        }
    }

    /**
     * 获取用户信息
     */
    @Override
    public void getUserInfo(AuthListener listener){

    }

}
