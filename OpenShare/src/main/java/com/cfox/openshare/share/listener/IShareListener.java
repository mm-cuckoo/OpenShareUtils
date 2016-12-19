package com.cfox.openshare.share.listener;

import com.cfox.openshare.SHARE_MEDIA;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.share.listener
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public interface IShareListener {

    void onSuccess(SHARE_MEDIA shareMedia);

    void onError(SHARE_MEDIA shareMedia, int errorCode, String errorMessage);

    void onCancel(SHARE_MEDIA shareMedia);
}
