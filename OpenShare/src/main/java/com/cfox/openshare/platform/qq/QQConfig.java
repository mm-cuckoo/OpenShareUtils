package com.cfox.openshare.platform.qq;

import com.cfox.openshare.AbsConfig;
import com.cfox.openshare.SHARE_MEDIA;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform.qq
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class QQConfig extends AbsConfig {
    @Override
    public SHARE_MEDIA getName() {
        return SHARE_MEDIA.QQ;
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
