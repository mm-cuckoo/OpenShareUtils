package com.cfox.openshare.platform.weixin;

import android.app.Activity;
import android.os.Bundle;

import com.cfox.openshare.PlatformConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform.weixin
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class WXEntryBaseActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI mWXApi;
    private WXConfig mWXConfig;

    private BaseResp resps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWXConfig = (WXConfig) PlatformConfig.configs.get(SHARE_MEDIA.WEIXIN);
        mWXApi = WXAPIFactory.createWXAPI(this, mWXConfig.appId, true);
        mWXApi.registerApp(mWXConfig.appId);
        mWXApi.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

        resps = baseResp;

        /**
         * ErrCode	ERR_OK = 0(用户同意)
         ERR_AUTH_DENIED = -4（用户拒绝授权）
         ERR_USER_CANCEL = -2（用户取消）
         */
        if(baseResp instanceof SendAuth.Resp){
            mWXConfig.mResp = (SendAuth.Resp) baseResp;
            mWXConfig.mWXCenter.loadAccessToken();
        }

        if (baseResp instanceof SendMessageToWX.Resp){
            SendMessageToWX.Resp resp = (SendMessageToWX.Resp) baseResp;
            mWXConfig.mWXShare.shareResult(resp);
        }
        finish();
    }
}
