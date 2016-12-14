package com.cfox.openshare.utils;

import android.util.Log;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.utils
 * <br/>AUTHOR : Machao
 * <br/>MSG :
 * <br/>************************************************
 */
public class OSLog {
    /** 如果将LOG_WE 修改为 false 将关闭app 中所有通过该类打出的log 信息*/
    public static boolean LOG_WE = true;
    private static final String LOG_TITLE = "###";

    public static void i(String tag, String msg) {
        if (LOG_WE) {
            Log.i(tag, LOG_TITLE + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_WE) {
            Log.e(tag, LOG_TITLE + msg.replaceAll("\r|\n|\t", ""));
        }
    }

    public static void d(String tag, String msg) {
        if (LOG_WE) {
            Log.d(tag, LOG_TITLE +  msg.replaceAll("\r|\n|\t", ""));
        }
    }


    public static void v(String tag, String msg) {
        if (LOG_WE) {
            Log.v(tag, LOG_TITLE +  msg.replaceAll("\r|\n|\t", ""));
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_WE) {
            Log.w(tag, LOG_TITLE +  msg.replaceAll("\r|\n|\t", ""));
        }
    }

    public static void s(String msg) {
        if (LOG_WE) {
            System.out.println( msg.replaceAll("\r|\n|\t", ""));
        }
    }

}
