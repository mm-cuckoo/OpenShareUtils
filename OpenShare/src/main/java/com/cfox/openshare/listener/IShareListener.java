package com.cfox.openshare.listener;

import com.cfox.openshare.SHARE_MEDIA;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.listener
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public interface IShareListener {

    void onSuccess(SHARE_MEDIA shareMedia);

    void onError(SHARE_MEDIA shareMedia,int errorCode,String errorMessage);

    void onCancel(SHARE_MEDIA shareMedia);
}
