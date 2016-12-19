package com.cfox.openshare.http;

import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.http
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public interface IHttpRequest {
    public void request(String url, Map<String, String> params, IStringCallBack callBack);
}
