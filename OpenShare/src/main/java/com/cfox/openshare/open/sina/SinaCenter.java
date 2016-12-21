package com.cfox.openshare.open.sina;

import android.app.Activity;

import com.cfox.openshare.OpenShareAPI;
import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.listener.IUserListener;
import com.cfox.openshare.open.ICenter;
import com.cfox.openshare.open.sina.api.UsersAPI;
import com.cfox.openshare.open.sina.http.SURL;
import com.cfox.openshare.open.sina.listener.SinaAuthListener;
import com.cfox.openshare.open.sina.listener.UserRequestListener;
import com.cfox.openshare.utils.OSLog;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open.sina
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class SinaCenter implements ICenter {

    private static final String TAG = "SinaCenter";

    private Activity mActivity;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private SinaConfig mSinaConfig;

    public SsoHandler getSsoHandler() {
        return mSsoHandler;
    }

    public SinaCenter(Activity activity){
        this.mActivity = activity;
        mSinaConfig = (SinaConfig) PlatformConfig.configs.get(SHARE_MEDIA.SIAN);
        if (mSinaConfig == null){
            OSLog.e(TAG,"place set appid and appSecret");
            return;
        }

        mAuthInfo = new AuthInfo(activity, mSinaConfig.appId, SURL.REDIRECT_URL, SURL.SCOPE);
        mSsoHandler = new SsoHandler(activity, mAuthInfo);
    }


    @Override
    public void login(IAuthListener listener) {
        SinaAuthListener authListener = new SinaAuthListener();
        authListener.mAuthListener = listener;
        mSsoHandler.authorize(authListener);
    }

    @Override
    public void getUserInfo(IUserListener listener) {
        // 获取用户信息接口
        UsersAPI usersAPI = new UsersAPI(mActivity, mSinaConfig.appId, mSinaConfig.mAccessToken);
        long uid = Long.parseLong(mSinaConfig.mAccessToken.getUid());
        usersAPI.show(uid, new UserRequestListener(listener, OpenShareAPI.getParse()));
    }

    @Override
    public void logout() {

    }
}
