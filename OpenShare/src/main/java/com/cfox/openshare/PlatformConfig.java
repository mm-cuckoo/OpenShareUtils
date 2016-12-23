package com.cfox.openshare;

import com.cfox.openshare.platform.qq.QQConfig;
import com.cfox.openshare.platform.sina.SinaConfig;
import com.cfox.openshare.platform.weixin.WXConfig;
import com.cfox.openshare.utils.OSLog;

import java.util.HashMap;
import java.util.Map;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare
 * <br/>AUTHOR : CFOX
 * <br/>MSG : 设置各个平台信息
 * <br/>************************************************
 */
public class PlatformConfig {

    public static Map<SHARE_MEDIA,IConfig> configs = new HashMap<>();

    public static void setQQ(String appId, String appKey){
        config(SHARE_MEDIA.QQ,appId,appKey);
    }

    public static void setWEIXIN(String appId,String appSecret){
        config(SHARE_MEDIA.WEIXIN,appId,appSecret);
    }

    public static void setSIAN(String appKey,String appSecret){
        config(SHARE_MEDIA.SIAN,appKey,appSecret);
    }

    /**
     * 设置是否开启log
     * @param l
     */
    public static void setLog(boolean l){
        OSLog.LOG_WE = l;
    }

    private static void config(SHARE_MEDIA media ,String appId,String appSecret){
        switch (media){
            case QQ:
                QQConfig qqConfig = (QQConfig) configs.get(media);
                if (qqConfig == null){
                    qqConfig = new QQConfig();
                }
                qqConfig.appId = appId;
                qqConfig.appSecret = appSecret;
                configs.put(media,qqConfig);
                break;

            case WEIXIN:
                WXConfig wxConfig = (WXConfig) configs.get(media);
                if (wxConfig == null){
                    wxConfig = new WXConfig();
                }
                wxConfig.appId = appId;
                wxConfig.appSecret = appSecret;
                configs.put(media,wxConfig);
                break;
            case SIAN:
                SinaConfig sinaConfig = (SinaConfig) configs.get(media);
                if (sinaConfig == null){
                    sinaConfig = new SinaConfig();
                }
                sinaConfig.appId = appId;
                sinaConfig.appSecret = appSecret;
                configs.put(media, sinaConfig);
                break;
        }
    }
}
