package com.cfox.openshare.share.qq.listener;

import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.share.listener.IShareListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.share.qq.listener
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class QShareListener implements IUiListener {

    public IShareListener mShareListener;

    public QShareListener() {
    }

    @Override
    public void onComplete(Object o) {

        if (mShareListener != null)
            mShareListener.onSuccess(SHARE_MEDIA.QQ);
    }

    @Override
    public void onError(UiError uiError) {
        if (mShareListener != null)
            mShareListener.onError(SHARE_MEDIA.QQ,uiError.errorCode,uiError.errorMessage);
    }

    @Override
    public void onCancel() {
        if (mShareListener != null)
            mShareListener.onCancel(SHARE_MEDIA.QQ);
    }
}
