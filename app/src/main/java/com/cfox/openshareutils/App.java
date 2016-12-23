package com.cfox.openshareutils;

import android.app.Application;

import com.cfox.openshare.OpenShareAPI;
import com.cfox.openshare.PlatformConfig;

public class App extends Application {
    //QQ
    public static final String QQ_APP_ID = "";
    public static final String QQ_APP_KEY = "";


    //微信
    public final static String WX_APP_ID = "";
    public final static String WX_APP_SECRET = "";


    //微博
    public final static String SINA_WEIBO_APP_KEY = "";
    public final static String SINA_WEIBO_APP_SECRET = "";
    @Override
    public void onCreate() {
        super.onCreate();

        PlatformConfig.setQQ(QQ_APP_ID,QQ_APP_KEY);
        PlatformConfig.setWEIXIN(WX_APP_ID,WX_APP_SECRET);
        PlatformConfig.setSIAN(SINA_WEIBO_APP_KEY,SINA_WEIBO_APP_SECRET);

        OpenShareAPI.setHttpRequest(new HttpReq());
        OpenShareAPI.setParse(new JsonParser());
    }
}
