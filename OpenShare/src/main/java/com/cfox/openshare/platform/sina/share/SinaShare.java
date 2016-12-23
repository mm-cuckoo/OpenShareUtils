package com.cfox.openshare.platform.sina.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.cfox.openshare.ICheckBack;
import com.cfox.openshare.OpenShareAPI;
import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.http.IBitmapCallBack;
import com.cfox.openshare.http.IHttpRequest;
import com.cfox.openshare.platform.sina.SinaConfig;
import com.cfox.openshare.platform.IShare;
import com.cfox.openshare.platform.ShareAPI;
import com.cfox.openshare.platform.ShareMedia;
import com.cfox.openshare.platform.sina.listener.SinaListener;
import com.cfox.openshare.utils.OSLog;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform.sina.share
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class SinaShare implements IShare {
    private static final String TAG = "SinaShare";

    private static final int THUMB_SIZE = 150;

    private Activity mActivity;

    /**
     * 微博分享的接口实例
     */
    private IWeiboShareAPI mWeiboShareAPI;
    private SinaConfig mSinaConfig;
    private SinaListener mListener;


    public SinaShare(Activity activity) {
        this.mActivity = activity;

        mSinaConfig = (SinaConfig) PlatformConfig.configs.get(SHARE_MEDIA.SIAN);
        if (mSinaConfig == null){
            OSLog.e(TAG,"place set appid and appSecret");
            return;
        }

        // 创建微博 SDK 接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(activity, mSinaConfig.appId);
        // 注册到新浪微博
        mWeiboShareAPI.registerApp();

        mListener = new SinaListener();

        mWeiboShareAPI.handleWeiboResponse(mActivity.getIntent(), mListener);
    }

    @Override
    public void share(ShareAPI shareAPI) {
        this.mListener.mShareListener = shareAPI.getShareListener();

        /**
         * 判断是否安装微信
         */
        ICheckBack checkBack = OpenShareAPI.getCheckBack();
        if (checkBack != null) {
            checkBack.installed(SHARE_MEDIA.SIAN,mWeiboShareAPI.isWeiboAppInstalled());
            if (!mWeiboShareAPI.isWeiboAppInstalled()) return;
        }


        /**
         * 分享图片为网络图片，进行网络请求图片
         */
        if (shareAPI.getShareMedia().getImageUrl() != null) {
            netImageRequestToBytes(shareAPI);
            return;
        }

        /**
         * 分享图片为本地进行本地加载
         */
        if (shareAPI.getShareMedia().getmImageRes() != -1) {
            localImageToBytes(shareAPI);
            return;
        }

        sendShare(null, shareAPI);
    }


    /**
     * 请求网络图片并转换成byte[]
     */
    private void netImageRequestToBytes(final ShareAPI shareAPI) {

        IHttpRequest request = OpenShareAPI.getHttpRequest();
        request.requestToBitmap(shareAPI.getShareMedia().getImageUrl(), new IBitmapCallBack() {
            @Override
            public void onSuccess(int code, Bitmap bitmap) {
                sendShare(bitmap, shareAPI);
            }

            @Override
            public void onError(int code, String errorMsg) {

            }
        });


    }

    /**
     * 获取本地图片并转换成byte[]
     *
     * @param shareAPI
     */
    private void localImageToBytes(ShareAPI shareAPI) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeResource(mActivity.getResources(), shareAPI.getShareMedia().getmImageRes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendShare(bitmap, shareAPI);
    }

    /**
     * 执行分享
     *
     * @param imageBitmap
     * @param shareAPI
     */
    private void sendShare(Bitmap imageBitmap, ShareAPI shareAPI) {

        Bitmap bitmap = null;
        if (imageBitmap != null) {
            bitmap = Bitmap.createScaledBitmap(imageBitmap, THUMB_SIZE, THUMB_SIZE, true);
            imageBitmap.recycle();
        }

        if (shareAPI.getShareMedia() == null){
            ShareMedia media = new ShareMedia();
            if (shareAPI.getTargetUrl() == null) {
                media.setMediaType(ShareMedia.SHARE_TEXT);
            }
            shareAPI.withMedia(media);
        }

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();


        switch (shareAPI.getShareMedia().getMediaType()) {

            case ShareMedia.SHARE_TEXT:
                /**
                 * share text
                 */
                weiboMessage.textObject = getTextObj();
                break;

            case ShareMedia.SHARE_IMGE:
                /**
                 * Share local image
                 */
                weiboMessage.mediaObject = getImageObj(bitmap);
                break;

            case ShareMedia.SHARE_MUSIC:
                /**
                 * share music
                 */
                weiboMessage.mediaObject = getMusicObj(bitmap,shareAPI);
                break;

            case ShareMedia.SHARE_VIDEO:
                /**
                 * share video
                 */
                weiboMessage.mediaObject = getVideoObj(bitmap,shareAPI);
                break;

            default:
                weiboMessage.mediaObject = getWebpageObj(bitmap,shareAPI);
        }


        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(mActivity, request);
    }



    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText();
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(Bitmap bitmap,ShareAPI shareAPI) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = shareAPI.getTitle();
        mediaObject.description = shareAPI.getText();

        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = shareAPI.getTargetUrl();
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }

    /**
     * 创建多媒体（音乐）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private MusicObject getMusicObj(Bitmap bitmap,ShareAPI shareAPI) {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = shareAPI.getTitle();
        musicObject.description = shareAPI.getText();


        // 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        musicObject.setThumbImage(bitmap);
        musicObject.actionUrl = shareAPI.getTargetUrl();
        musicObject.dataUrl = "www.weibo.com";
        musicObject.dataHdUrl = "www.weibo.com";
        musicObject.duration = 10;
        musicObject.defaultText = "Music 默认文案";
        return musicObject;
    }

    /**
     * 创建多媒体（视频）消息对象。
     *
     * @return 多媒体（视频）消息对象。
     */
    private VideoObject getVideoObj(Bitmap bitmap,ShareAPI shareAPI) {
        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = shareAPI.getTitle();
        videoObject.description = shareAPI.getText();
        // 设置 Bitmap 类型的图片到视频对象里  设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。


        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
            System.out.println("kkkkkkk    size  "+ os.toByteArray().length );
        } catch (Exception e) {
            e.printStackTrace();
            OSLog.e("Weibo.BaseMediaObject", "put thumb failed");
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        videoObject.setThumbImage(bitmap);
        videoObject.actionUrl = shareAPI.getTargetUrl();
        videoObject.dataUrl = "www.weibo.com";
        videoObject.dataHdUrl = "www.weibo.com";
        videoObject.duration = 10;
        videoObject.defaultText = "Vedio 默认文案";
        return videoObject;
    }

    /**
     * 创建多媒体（音频）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private VoiceObject getVoiceObj(Bitmap bitmap,ShareAPI shareAPI) {
        // 创建媒体消息
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title = shareAPI.getTitle();
        voiceObject.description = shareAPI.getText();
        // 设置 Bitmap 类型的图片到视频对象里      设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        voiceObject.setThumbImage(bitmap);
        voiceObject.actionUrl = shareAPI.getTargetUrl();
        voiceObject.dataUrl = "www.weibo.com";
        voiceObject.dataHdUrl = "www.weibo.com";
        voiceObject.duration = 10;
        voiceObject.defaultText = "Voice 默认文案";
        return voiceObject;
    }

    public String getSharedText() {
        return null;
    }
}
