package com.cfox.openshare.open;

import android.app.Activity;
import android.content.Intent;

import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.listener.IUserListener;
import com.cfox.openshare.open.qq.QQCenter;
import com.cfox.openshare.open.qq.listener.QOpenListener;
import com.cfox.openshare.open.sina.SinaCenter;
import com.cfox.openshare.open.weixin.WXCenter;
import com.cfox.openshare.utils.OSLog;
import com.tencent.tauth.Tencent;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class OpenAPI {

    private static final String TAG = "OpenAPI";

    private Activity mActivity;

    private static ICenter mCenter;

    private static SHARE_MEDIA mShareMedia;

    public OpenAPI(Activity activity){
        this.mActivity = activity;
    }

    public static OpenAPI initOpen(Activity activity){
        return new OpenAPI(activity);
    }

    /**
     * 验证授权分发
     * @param shareMedia 验证类型
     * @param listener 验证监听
     */
    public void doAuthVerify(SHARE_MEDIA shareMedia, IAuthListener listener){
        mShareMedia = shareMedia;
        switch (shareMedia){
            case QQ:
                qqAuthVerify(listener);
                break;

            case WEIXIN:
                wxAuthVerify(listener);
                break;

            case SIAN:
                sinaAuthVerify(listener);
                break;
        }
    }

    /**
     * 获取用户信息
     * @param shareMedia 验证类型
     * @param listener 验证监听
     */
    public void getUserInfo(SHARE_MEDIA shareMedia, IUserListener listener){
        mShareMedia = shareMedia;
        switch (shareMedia){
            case QQ:
                qqUserInfo(listener);
                break;

            case WEIXIN:
                wxInfo(listener);
                break;

            case SIAN:
                sinaUserInfo(listener);
                break;
        }
    }

    /**
     * 登出
     */
    public void logout(){
        if (mCenter == null){
            OSLog.e(TAG,TAG + " class is null ");
            return;
        }
        mCenter.logout();
    }

    /**
     * qq 授权验证
     * @param listener 验证监听
     */
    private void qqAuthVerify(IAuthListener listener){
        getQQCenter();
        mCenter.login(listener);
    }

    /**
     * qq 个人信息
     * @param listener 信息监听
     */
    private void qqUserInfo(IUserListener listener){
        getQQCenter();
        mCenter.getUserInfo(listener);
    }

    private void getQQCenter() {
        if (mCenter == null){
            mCenter = new QQCenter(mActivity);
        }

        if (!(mCenter instanceof QQCenter)){
            mCenter = new QQCenter(mActivity);
        }
    }


    /**
     * 微信授权验证
     * @param listener 验证监听
     */
    private void wxAuthVerify(IAuthListener listener){
        getWXCenter();
        mCenter.login(listener);
    }

    /**
     * 微信 个人信息
     * @param listener 信息监听
     */
    private void wxInfo(IUserListener listener){
        getWXCenter();
        mCenter.getUserInfo(listener);
    }

    private void getWXCenter() {
        if (mCenter == null){
            mCenter = new WXCenter(mActivity);
        }

        if (!(mCenter instanceof WXCenter)){
            mCenter = new WXCenter(mActivity);
        }
    }

    /**
     * 新浪授权验证
     * @param listener 验证监听
     */
    private void sinaAuthVerify(IAuthListener listener){

        getSinaCenter();
        mCenter.login(listener);

    }


    /**
     * 新浪微博 个人信息
     * @param listener
     */
    private void sinaUserInfo(IUserListener listener) {

        getSinaCenter();
        mCenter.getUserInfo(listener);
    }

    private void getSinaCenter() {
        if (mCenter == null){
            mCenter = new SinaCenter(mActivity);
        }

        if (!(mCenter instanceof SinaCenter)){
            mCenter = new SinaCenter(mActivity);
        }
    }


    public static void activityResultData(int requestCode, int resultCode, Intent data){
        if (mShareMedia == SHARE_MEDIA.QQ){
            Tencent.onActivityResultData(requestCode,resultCode,data,new QOpenListener());
        }

        if (mShareMedia == SHARE_MEDIA.SIAN){
            SinaCenter mCenter = (SinaCenter) OpenAPI.mCenter;
            mCenter.getSsoHandler().authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
