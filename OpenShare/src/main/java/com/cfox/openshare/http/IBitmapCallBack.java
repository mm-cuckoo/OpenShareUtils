package com.cfox.openshare.http;

import android.graphics.Bitmap;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.http
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public interface IBitmapCallBack {
    void onSuccess(int code, Bitmap bitmap);
    void onError(int code, String errorMsg);
}
