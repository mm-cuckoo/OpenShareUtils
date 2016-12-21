package com.cfox.openshare.open.qq;

import android.app.Activity;

import com.cfox.openshare.OpenShareAPI;
import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.listener.IUserListener;
import com.cfox.openshare.open.ICenter;
import com.cfox.openshare.open.qq.listener.QOpenListener;
import com.cfox.openshare.utils.OSLog;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.Tencent;

import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open.qq
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class QQCenter implements ICenter {

    private static final String TAG = "QQCenter";
    private Activity mActivity;
    private Tencent mTencent;

    private QQConfig mQQConfig;


    public QQCenter(Activity activity){
        this.mActivity = activity;
        mQQConfig = (QQConfig) PlatformConfig.configs.get(SHARE_MEDIA.QQ);
        if (mQQConfig == null){
            OSLog.e(TAG,"place set appid and appSecret");
            return;
        }
        mTencent = Tencent.createInstance(mQQConfig.appId, mActivity.getApplicationContext());
    }

    /**
     * 登录QQ
     */
    @Override
    public void login(IAuthListener listener){
        if (!mTencent.isSessionValid()) {
            QOpenListener QOpenListener = new QOpenListener();
            QOpenListener.sign = com.cfox.openshare.open.qq.listener.QOpenListener.LOGIN;
            QOpenListener.mParse = OpenShareAPI.getParse();
            QOpenListener.mListener = listener;
            mTencent.login(mActivity, "all", QOpenListener);
        }else {
            OSLog.e("openshare","Please set the AppId and appSecret");
        }
    }

    /**
     * QQ 登出
     */
    @Override
    public void logout() {
        mTencent.logout(mActivity);
    }


    /**
     * 获取用户信息
     */
    @Override
    public void getUserInfo(IUserListener listener){

        QQConfig qqConfig = (QQConfig) PlatformConfig.configs.get(SHARE_MEDIA.QQ);

        if (qqConfig == null){
            OSLog.e("openshare","place set appid and appSecret");
            return;
        }
        Map<String,String> authInfo = qqConfig.authInfo;

        String openId = authInfo.get("openid");
        String accessToken = authInfo.get("access_token");
        String expiresIn = authInfo.get("expires_in");

        mTencent.setOpenId(openId);
        mTencent.setAccessToken(accessToken,expiresIn);


        QOpenListener QOpenListener = new QOpenListener();
        QOpenListener.sign = com.cfox.openshare.open.qq.listener.QOpenListener.USER_INFO;
        QOpenListener.mParse = OpenShareAPI.getParse();
        QOpenListener.mListener = listener;

        UserInfo userInfo = new UserInfo(mActivity, mTencent.getQQToken());
        userInfo.getUserInfo(QOpenListener);
    }
}
