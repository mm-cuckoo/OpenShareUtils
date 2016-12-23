package com.cfox.openshare.platform.weixin.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.cfox.openshare.ICheckBack;
import com.cfox.openshare.OpenShareAPI;
import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.http.IBitmapCallBack;
import com.cfox.openshare.http.IHttpRequest;
import com.cfox.openshare.platform.weixin.WXConfig;
import com.cfox.openshare.platform.IShare;
import com.cfox.openshare.platform.ShareAPI;
import com.cfox.openshare.platform.ShareMedia;
import com.cfox.openshare.listener.IShareListener;
import com.cfox.openshare.utils.OSLog;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXMusicObject;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXVideoObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform.weixin.share
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class WXShare implements IShare {
    private static final String TAG = "WXShare";

    private static final int THUMB_SIZE = 150;

    private IWXAPI mWXApi;

    private Activity mActivity;
    private WXConfig mWXConfig;
    private SHARE_MEDIA mPlatform = SHARE_MEDIA.WEIXIN;
    private IShareListener mShareListener;

    public WXShare(Activity mActivity, SHARE_MEDIA platform) {
        this.mActivity = mActivity;
        this.mPlatform = platform;
        mWXConfig = (WXConfig) PlatformConfig.configs.get(SHARE_MEDIA.WEIXIN);
        if (mWXConfig == null) {
            OSLog.e(TAG, "place set appid and appSecret");
            return;
        }

        mWXApi = WXAPIFactory.createWXAPI(mActivity, mWXConfig.appId, true);
        mWXApi.registerApp(mWXConfig.appId);
        mWXConfig.mWXShare = this;
    }


    public void shareResult(SendMessageToWX.Resp resp) {

        if (mShareListener == null) return;
        /**
         * ErrCode	ERR_OK = 0(用户同意)
         ERR_AUTH_DENIED = -4（用户拒绝授权）
         ERR_USER_CANCEL = -2（用户取消）
         */
        if (resp.errCode == 0) {
            mShareListener.onSuccess(SHARE_MEDIA.WEIXIN);
            return;
        }

        if (resp.errCode == -2) {
            mShareListener.onCancel(SHARE_MEDIA.WEIXIN);
            return;
        }

        if (resp.errCode == -4) {
            mShareListener.onError(SHARE_MEDIA.WEIXIN, resp.errCode, resp.errStr);
        }
    }

    @Override
    public void share(ShareAPI shareAPI) {

        /**
         * 判断是否安装微信
         */
        ICheckBack checkBack = OpenShareAPI.getCheckBack();
        if (checkBack != null) {
            checkBack.installed(SHARE_MEDIA.WEIXIN,mWXApi.isWXAppInstalled());
            if (!mWXApi.isWXAppInstalled()) return;
        }

        this.mShareListener = shareAPI.getShareListener();
        /**
         * 如果 ShareMedia 为 null 分享内容一定是纯文本
         */
        if (shareAPI.getShareMedia() == null) {
            sendShare(null, shareAPI);
            return;
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

        switch (shareAPI.getShareMedia().getMediaType()) {

            case ShareMedia.SHARE_TEXT:
                /**
                 * share text
                 */
                shareText(shareAPI);
                break;

            case ShareMedia.SHARE_IMGE:
                /**
                 * Share local image
                 */
                shareImage(bitmap, shareAPI);
                break;

            case ShareMedia.SHARE_MUSIC:
                /**
                 * share music
                 */
                shareMusic(bitmap, shareAPI);
                break;

            case ShareMedia.SHARE_VIDEO:
                /**
                 * share video
                 */
                shareVideo(bitmap, shareAPI);
                break;

            default:
                shareHtml(bitmap, shareAPI);
        }
    }


    private SendMessageToWX.Req buildSendMessage(ShareAPI shareAPI) {

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(shareAPI.getText());

        if (mPlatform == SHARE_MEDIA.WEIXIN_CIRCLE) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
            return req;
        }

        if (mPlatform == SHARE_MEDIA.WEIXIN) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }

        return req;
    }

    /**
     * 分享文字
     *
     * @param shareAPI
     */
    private void shareText(ShareAPI shareAPI) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = shareAPI.getText();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = shareAPI.getText();

        SendMessageToWX.Req req = buildSendMessage(shareAPI);
        req.message = msg;
        mWXApi.sendReq(req);

    }

    /**
     * 分享图片
     *
     * @param shareAPI
     */
    private void shareImage(Bitmap thumBmp, ShareAPI shareAPI) {

        WXImageObject imageObject = new WXImageObject(thumBmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;

        if (thumBmp != null)
            msg.thumbData = bmpToByteArray(thumBmp, true);

        SendMessageToWX.Req req = buildSendMessage(shareAPI);
        req.message = msg;

        mWXApi.sendReq(req);
    }

    /**
     * 分享音乐
     *
     * @param shareAPI
     */
    private void shareMusic(Bitmap thumBmp, ShareAPI shareAPI) {
        WXMusicObject musicObject = new WXMusicObject();
        musicObject.musicUrl = shareAPI.getTargetUrl();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = musicObject;
        msg.title = shareAPI.getTitle();
        msg.description = shareAPI.getText();

        if (thumBmp != null)
            msg.thumbData = bmpToByteArray(thumBmp, true);

        SendMessageToWX.Req req = buildSendMessage(shareAPI);
        req.message = msg;

        mWXApi.sendReq(req);
    }

    /**
     * 分享视频
     *
     * @param shareAPI
     */
    private void shareVideo(Bitmap thumBmp, ShareAPI shareAPI) {

        WXVideoObject videoObject = new WXVideoObject();
        videoObject.videoUrl = shareAPI.getTargetUrl();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = videoObject;
        msg.title = shareAPI.getTitle();
        msg.description = shareAPI.getText();

        if (thumBmp != null)
            msg.thumbData = bmpToByteArray(thumBmp, true);


        SendMessageToWX.Req req = buildSendMessage(shareAPI);
        req.message = msg;

        mWXApi.sendReq(req);
    }

    /**
     * 分享 html
     *
     * @param shareAPI
     */
    private void shareHtml(Bitmap thumBmp, ShareAPI shareAPI) {

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = shareAPI.getTargetUrl();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = webpageObject;
        msg.title = shareAPI.getTitle();
        msg.description = shareAPI.getText();

        if (thumBmp != null)
            msg.thumbData = bmpToByteArray(thumBmp, true);

        SendMessageToWX.Req req = buildSendMessage(shareAPI);
        req.message = msg;

        mWXApi.sendReq(req);

    }

    /**
     * 得到Bitmap的byte
     *
     * @param bmp 图片
     * @return 返回压缩的图片
     */
    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);

        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();

        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
