package com.cfox.openshare.platform;

import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.listener.IUserListener;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public interface ICenter {

    public void login(IAuthListener listener);

    public void getUserInfo(IUserListener listener);

    public void logout();
}
