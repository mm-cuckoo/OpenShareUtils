package com.cfox.openshare.open;

import android.content.Context;

import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.AuthListener;
import com.cfox.openshare.open.qq.QQCenter;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class OpenAPI {

    private Context mContext;

    private ICenter mCenter;

    public OpenAPI(Context context){
        this.mContext = context;
    }

    public static OpenAPI initOpen(Context context){
        return new OpenAPI(context);
    }

    /**
     * 验证授权分发
     * @param shareMedia 验证类型
     * @param listener 验证监听
     */
    public void doAuthVerify(SHARE_MEDIA shareMedia, AuthListener listener){
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

    public void getUserInfo(SHARE_MEDIA shareMedia, AuthListener listener){
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
     * qq 授权验证
     * @param listener 验证监听
     */
    private void qqAuthVerify(AuthListener listener){

        if (mCenter == null){
            mCenter = new QQCenter(mContext);
        }

        if (!(mCenter instanceof QQCenter)){
            mCenter = new QQCenter(mContext);
        }

        mCenter.login(listener);
    }

    private void qqUserInfo(AuthListener listener){

        if (mCenter == null){
            mCenter = new QQCenter(mContext);
        }

        if (!(mCenter instanceof QQCenter)){
            mCenter = new QQCenter(mContext);
        }

        mCenter.login(listener);
    }



    /**
     * 微信授权验证
     * @param listener 验证监听
     */
    private void wxAuthVerify(AuthListener listener){

    }

    /**
     * 新浪授权验证
     * @param listener 验证监听
     */
    private void sinaAuthVerify(AuthListener listener){

    }
}
