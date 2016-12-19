package com.cfox.openshare;

import com.cfox.openshare.http.IHttpRequest;
import com.cfox.openshare.parse.IParse;
import com.cfox.openshare.parse.JsonParse;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class OpenShareAPI {

    private static IParse sParse;

    private static IHttpRequest sHttpRequest;

    public static void setParse(IParse parse){
        OpenShareAPI.sParse = parse;
    }

    public static IParse getParse(){
        if (OpenShareAPI.sParse == null){
            OpenShareAPI.sParse = new JsonParse();
        }
        return OpenShareAPI.sParse;
    }

    public static IHttpRequest getsHttpRequest() {
        return sHttpRequest;
    }

    public static void setsHttpRequest(IHttpRequest sHttpRequest) {
        OpenShareAPI.sHttpRequest = sHttpRequest;
    }
}
