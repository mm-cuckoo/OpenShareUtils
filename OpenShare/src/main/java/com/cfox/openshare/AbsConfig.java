package com.cfox.openshare;

import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public abstract class AbsConfig implements IConfig {

    public String appId;
    public String appSecret;
    public Map<String,String> authInfo;
    public Map<String,String> userInfo;
}
