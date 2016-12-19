package com.cfox.openshare.open.qq.listener;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.open.qq.listener
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class UiListener implements IUiListener{
    public static final int LOGIN = 0x001;
    public static final int USER_INFO = 0x002;

    private int sign = 0;
    private AuthListener mListener;

    public UiListener(int flage,AuthListener listener){
        this.sign = flage;
        this.mListener = listener;
    }

    @Override
    public void onComplete(Object o) {



    }

    @Override
    public void onError(UiError uiError) {

    }

    @Override
    public void onCancel() {

    }
}
