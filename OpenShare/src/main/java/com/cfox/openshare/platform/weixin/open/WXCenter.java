package com.cfox.openshare.platform.weixin.open;

import android.app.Activity;

import com.cfox.openshare.ICheckBack;
import com.cfox.openshare.OpenShareAPI;
import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.http.IHttpRequest;
import com.cfox.openshare.http.IStringCallBack;
import com.cfox.openshare.http.ReqBuild;
import com.cfox.openshare.listener.IBackListener;
import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.listener.IUserListener;
import com.cfox.openshare.platform.ICenter;
import com.cfox.openshare.platform.weixin.WXConfig;
import com.cfox.openshare.platform.weixin.http.WXRequest;
import com.cfox.openshare.platform.weixin.listener.WXCallbackListener;
import com.cfox.openshare.utils.OSLog;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;

import static com.tencent.mm.sdk.modelbase.BaseResp.ErrCode.ERR_AUTH_DENIED;
import static com.tencent.mm.sdk.modelbase.BaseResp.ErrCode.ERR_OK;
import static com.tencent.mm.sdk.modelbase.BaseResp.ErrCode.ERR_USER_CANCEL;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform.weixin.open
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class WXCenter implements ICenter{
    private static final String TAG = "WXCenter";

    private IWXAPI mWXApi;

    private WXConfig mWXConfig;

    private IBackListener mIBackListener;

    private WXCallbackListener mWXCallbackListener;



    public WXCenter(Activity activity) {
        mWXConfig = (WXConfig) PlatformConfig.configs.get(SHARE_MEDIA.WEIXIN);
        if (mWXConfig == null){
            OSLog.e(TAG,"place set appid and appSecret");
            return;
        }
        mWXApi = WXAPIFactory.createWXAPI(activity, mWXConfig.appId, true);
        mWXApi.registerApp(mWXConfig.appId);
        mWXConfig.mWXCenter = this;
        mWXCallbackListener = new WXCallbackListener();
        mWXCallbackListener.mParse = OpenShareAPI.getParse();
    }

    public void loadAccessToken() {

        SendAuth.Resp resp = mWXConfig.mResp;
        if (resp == null){
            OSLog.e(TAG,"resp is null");
            return;
        }

        /**
         ErrCode ERR_OK = 0(用户同意)
         ERR_AUTH_DENIED = -4（用户拒绝授权）
         ERR_USER_CANCEL = -2（用户取消）
         */

        if (ERR_OK == resp.errCode ){
            mWXCallbackListener.sign = WXCallbackListener.LOGIN;
            mWXCallbackListener.mListener = mIBackListener;

            ReqBuild build = WXRequest.getAccessTokenBuild(mWXConfig.appId,mWXConfig.appSecret,resp.code);

            IHttpRequest request = OpenShareAPI.getHttpRequest();
            request.requestToSting(build, new IStringCallBack() {
                @Override
                public void onSuccess(int code, String str) {
                    mWXCallbackListener.onComplete(str);
                }

                @Override
                public void onError(int code, String errorMsg) {
                    mWXCallbackListener.onError(code,errorMsg);
                }
            });
            return;
        }

        if (ERR_AUTH_DENIED == resp.errCode){
            mWXCallbackListener.onError(-1,"SendAuth.Resp errCode is ERR_AUTH_DENIED");
            return;
        }

        if (ERR_USER_CANCEL == resp.errCode){
            mWXCallbackListener.onCancel();
            return;
        }
    }

    /**
     * 登录微信
     * @param listener
     */
    @Override
    public void login(IAuthListener listener) {

        /**
         * 判断是否安装微信
         */
        ICheckBack checkBack = OpenShareAPI.getCheckBack();
        if (checkBack != null) {
            checkBack.installed(SHARE_MEDIA.WEIXIN,mWXApi.isWXAppInstalled());
            if (!mWXApi.isWXAppInstalled()) return;
        }

        mIBackListener = listener;
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "weixin_state";
        mWXApi.sendReq(req);
    }

    @Override
    public void getUserInfo(final IUserListener listener) {
        Map<String,String> authInfo = mWXConfig.authInfo;

        if (authInfo == null){
            OSLog.e(TAG,"authInfo is null");
            return;
        }

        String accessToken = authInfo.get("access_token");
        String openId = authInfo.get("openid");

        mWXCallbackListener.sign = WXCallbackListener.USER_INFO;
        mWXCallbackListener.mListener = mIBackListener = listener;
        ReqBuild build = WXRequest.getUserInfoBuild(accessToken,openId);
        IHttpRequest request = OpenShareAPI.getHttpRequest();
        request.requestToSting(build, new IStringCallBack() {
            @Override
            public void onSuccess(int code, String str) {
                mWXCallbackListener.onComplete(str);
            }

            @Override
            public void onError(int code, String errorMsg) {
                mWXCallbackListener.onError(code,errorMsg);
            }
        });
    }

    @Override
    public void logout() {

    }
}
