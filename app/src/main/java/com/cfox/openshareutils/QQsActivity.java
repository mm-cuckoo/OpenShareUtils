package com.cfox.openshareutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.listener.IUserListener;
import com.cfox.openshare.platform.OpenAPI;
import com.cfox.openshare.platform.ShareAPI;
import com.cfox.openshare.platform.ShareMedia;

import java.util.Map;

public class QQsActivity extends Activity {
    private OpenAPI mOpenAPI;

    private Map<String,String> maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mOpenAPI = OpenAPI.initOpen(this);



    }

    public void login(View view) {
        loginQQ();
    }

    public void logOut(View view) {
        logout();
    }

    /**
     * 获取QQ 信息
     *
     * @param view
     */
    public void getInfo(View view) {

        mOpenAPI.getUserInfo(SHARE_MEDIA.QQ, new IUserListener() {
            @Override
            public void onComplete(SHARE_MEDIA shareMedia, Map<String, String> map) {
                maps = map;
            }

            @Override
            public void onError(SHARE_MEDIA shareMedia, int errorCode, String errorMessage) {

            }
        });

    }

    public void openShare(View view) {
        onClickShare();
    }

    /**
     * 登录qq
     */
    public void loginQQ() {
        mOpenAPI.doAuthVerify(SHARE_MEDIA.QQ, new IAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA shareMedia, Map<String, String> map) {
                maps = map;
            }

            @Override
            public void onError(SHARE_MEDIA shareMedia, int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel(SHARE_MEDIA shareMedia) {

            }
        });
    }


    /**
     * 注销
     */
    public void logout() {
        mOpenAPI.logout();
    }

    /**
     * 分享到QQ
     */
    private void onClickShare() {
        ShareAPI shareAPI = new ShareAPI(this);
        ShareMedia shareMedia = new ShareMedia();
        shareMedia.setMediaType(ShareMedia.SHARE_DEFAULT);
        shareMedia.setImageUrl("http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        shareAPI.setPlatform(SHARE_MEDIA.QZONE);
        shareAPI.withTitle("马超测试分享")
                .withText("这里是测试分享内容")
                .withTargetUrl("http://www.baidu.com")
                .withMedia(shareMedia)
                .share();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        OpenAPI.activityResultData(requestCode,resultCode,data);
        ShareAPI.activityResultData(requestCode,resultCode,data);

    }

}