package com.cfox.openshare.share.qq;

import android.app.Activity;
import android.os.Bundle;

import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.open.qq.QQConfig;
import com.cfox.openshare.share.IShare;
import com.cfox.openshare.share.ShareAPI;
import com.cfox.openshare.share.ShareMedia;
import com.cfox.openshare.share.qq.listener.QShareListener;
import com.cfox.openshare.utils.OSLog;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.share.qq
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class QShare implements IShare {
    private static final String TAG = "QShare";

    private Activity mActivity;
    private Tencent mTencent;

    private QQConfig mQQConfig;
    public QShare(Activity activity) {
        this.mActivity = activity;
        mQQConfig = (QQConfig) PlatformConfig.configs.get(SHARE_MEDIA.QQ);
        if (mQQConfig == null){
            OSLog.e("openshare","place set appid and appSecret");
            return;
        }
        mTencent = Tencent.createInstance(mQQConfig.appId, mActivity.getApplicationContext());
    }


    @Override
    public void share(ShareAPI shareAPI) {
        shareDefault(shareAPI);
    }

    private void shareDefault(ShareAPI shareAPI){
        final Bundle params = new Bundle();
        QShareListener listener = new QShareListener();
        listener.mShareListener = shareAPI.getShareListener();

        if (shareAPI.getTargetUrl() == null){
            OSLog.e(TAG,"TargetUrl is null");
            return;
        }

        if (shareAPI.getShareMedia() == null){
            shareAPI.withMedia(new ShareMedia());
        }

        switch (shareAPI.getShareMedia().getMediaType()){
            case ShareMedia.SHARE_IMGE:
                /**
                 * Share local image
                 */
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,shareAPI.getShareMedia().getImageUrl());
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, shareAPI.getAppName());
                break;

            case ShareMedia.SHARE_MUSIC:
                /**
                 * share music
                 */
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, shareAPI.getTitle());
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  shareAPI.getText());
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  shareAPI.getTargetUrl());
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareAPI.getShareMedia().getImageUrl());
                params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, shareAPI.getShareMedia().getMediaUrl());
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  shareAPI.getAppName());
                break;

            default:
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, shareAPI.getTitle());
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  shareAPI.getText());
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  shareAPI.getTargetUrl());
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareAPI.getShareMedia().getImageUrl());
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  shareAPI.getAppName());
        }

        mTencent.shareToQQ(mActivity, params,listener);
    }
}
