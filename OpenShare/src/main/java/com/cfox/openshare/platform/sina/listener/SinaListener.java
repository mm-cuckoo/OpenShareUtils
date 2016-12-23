package com.cfox.openshare.platform.sina.listener;

import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.IShareListener;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.share.sina.listener
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class SinaListener implements IWeiboHandler.Response {

    public IShareListener mShareListener;

    public SinaListener() {
    }

    public SinaListener(IShareListener mShareListener) {
        this.mShareListener = mShareListener;
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {

        if (baseResponse != null) {
            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    mShareListener.onSuccess(SHARE_MEDIA.SIAN);
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    mShareListener.onCancel(SHARE_MEDIA.SIAN);
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    mShareListener.onError(SHARE_MEDIA.SIAN,baseResponse.errCode,baseResponse.errMsg);
                    break;
            }
        }
    }
}
