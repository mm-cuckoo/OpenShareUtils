package com.cfox.openshare.http;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.http
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public interface IHttpRequest {
    void requestToSting(ReqBuild reqBuild, IStringCallBack callBack);
    void requestToBitmap(String reqUrl, IBitmapCallBack callBack);
}
