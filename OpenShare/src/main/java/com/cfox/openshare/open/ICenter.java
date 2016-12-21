package com.cfox.openshare.open;

import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.listener.IUserListener;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public interface ICenter {

    public void login(IAuthListener listener);

    public void getUserInfo(IUserListener listener);

    public void logout();
}
