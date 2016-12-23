package com.cfox.openshare.platform;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.platform.qq.share.QZoneShare;
import com.cfox.openshare.listener.IShareListener;
import com.cfox.openshare.platform.qq.share.QShare;
import com.cfox.openshare.platform.qq.listener.QShareListener;
import com.cfox.openshare.platform.sina.share.SinaShare;
import com.cfox.openshare.platform.weixin.share.WXShare;
import com.tencent.tauth.Tencent;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class ShareAPI {

    private Activity mActivity;

    private static SHARE_MEDIA sPlatform;   //分享平台
    private String mAppName;                //应用名称
    private String mTitle;                  //分享title
    private String mText;                   //分享内容
    private String mTargetUrl;              //点击跳转
    private ShareMedia mShareMedia;
    private IShare mShare;
    private IShareListener mShareListener;  //分享结果回调

    public ShareAPI(Activity activity) {
        this.mActivity = activity;
        //获取应用名称
        PackageManager pm = activity.getPackageManager();
        this.mAppName = activity.getApplicationInfo().loadLabel(pm).toString();
    }

    /**
     * 设置分享平台
     *
     * @param platform
     * @return
     */
    public ShareAPI setPlatform(SHARE_MEDIA platform) {
        this.sPlatform = platform;

        switch (platform) {
            case QQ:
                mShare = new QShare(mActivity);
                break;

            case WEIXIN:
            case WEIXIN_CIRCLE:
                mShare = new WXShare(mActivity,platform);
                break;

            case SIAN:
                mShare = new SinaShare(mActivity);
                break;

            case QZONE:
                mShare = new QZoneShare(mActivity);
        }

        return this;
    }

    /**
     * 设置分享标题
     *
     * @param title
     * @return
     */
    public ShareAPI withTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 设置分享内容
     *
     * @param text
     * @return
     */
    public ShareAPI withText(String text) {
        this.mText = text;
        return this;
    }

    /**
     * 设置分享后点击跳转页面
     *
     * @param targetUrl
     * @return
     */
    public ShareAPI withTargetUrl(String targetUrl) {
        this.mTargetUrl = targetUrl;
        return this;
    }

    public ShareAPI withMedia(ShareMedia shareMedia) {
        mShareMedia = shareMedia;
        return this;
    }

    /**
     * 设置分享回调监听
     *
     * @param listener
     * @return
     */
    public ShareAPI setShareListener(IShareListener listener) {
        this.mShareListener = listener;
        return this;
    }

    public void share() {
        mShare.share(this);
    }

    public static void activityResultData(int requestCode, int resultCode, Intent data) {
        if (ShareAPI.sPlatform == SHARE_MEDIA.QQ) {
            Tencent.onActivityResultData(requestCode, resultCode, data, new QShareListener());
        }
    }

    public static SHARE_MEDIA getPlatform() {
        return sPlatform;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getText() {
        return mText;
    }

    public String getTargetUrl() {
        return mTargetUrl;
    }

    public ShareMedia getShareMedia() {
        return mShareMedia;
    }

    public IShare getShare() {
        return mShare;
    }

    public IShareListener getShareListener() {
        return mShareListener;
    }

    public String getAppName() {
        return mAppName;
    }
}
