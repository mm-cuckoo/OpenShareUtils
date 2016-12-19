package com.cfox.openshare.listener;

import com.cfox.openshare.SHARE_MEDIA;

import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.listener
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public interface IAuthListener {

    void onComplete(SHARE_MEDIA shareMedia, Map<String, String> map);

    void onError(SHARE_MEDIA shareMedia, int errorCode, String errorMessage);

    void onCancel(SHARE_MEDIA shareMedia);
}
