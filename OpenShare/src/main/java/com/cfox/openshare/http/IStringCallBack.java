package com.cfox.openshare.http;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.http
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public interface IStringCallBack {
    public void onSuccess(int code, String str);
    public void onError(int code, String errorMsg);
}
