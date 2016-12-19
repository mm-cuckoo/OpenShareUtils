package com.cfox.openshare.open;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public interface ICenter {

    public void login(AuthListener listener);

    public void getUserInfo(AuthListener listener);
}
