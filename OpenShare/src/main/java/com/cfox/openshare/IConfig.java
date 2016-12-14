package com.cfox.openshare;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public interface IConfig {

    SHARE_MEDIA getName();

//    void parse(JSONObject var1);

    boolean isConfigured();

    boolean isAuthrized();

}
