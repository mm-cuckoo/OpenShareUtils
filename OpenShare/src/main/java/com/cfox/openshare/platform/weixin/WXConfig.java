package com.cfox.openshare.platform.weixin;

import com.cfox.openshare.AbsConfig;
import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.platform.weixin.open.WXCenter;
import com.cfox.openshare.platform.weixin.share.WXShare;
import com.tencent.mm.sdk.modelmsg.SendAuth;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform.weixin
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class WXConfig  extends AbsConfig {
    public SendAuth.Resp mResp;

    public WXCenter mWXCenter;
    public WXShare mWXShare;

    @Override
    public SHARE_MEDIA getName() {
        return SHARE_MEDIA.WEIXIN;
    }

    @Override
    public boolean isConfigured() {
        return !(appId == null || appSecret == null);
    }

    @Override
    public boolean isAuthrized() {
        return authInfo != null;
    }
}
