package com.cfox.openshare.open.qq;

import com.cfox.openshare.AbsConfig;
import com.cfox.openshare.SHARE_MEDIA;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open.qq
 * <br/>AUTHOR : Machao
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
