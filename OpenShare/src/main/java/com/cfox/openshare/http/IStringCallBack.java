package com.cfox.openshare.http;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.http
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public interface IStringCallBack {
    void onSuccess(int code,String str);
    void onError(int code ,String errorMsg);
}
