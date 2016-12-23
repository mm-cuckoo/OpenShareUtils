package com.cfox.openshareutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cfox.openshare.SHARE_MEDIA;
import com.cfox.openshare.listener.IAuthListener;
import com.cfox.openshare.listener.IShareListener;
import com.cfox.openshare.listener.IUserListener;
import com.cfox.openshare.platform.OpenAPI;
import com.cfox.openshare.platform.ShareAPI;
import com.cfox.openshare.platform.ShareMedia;

import java.util.Map;

public class SinasActivity extends Activity {

    private OpenAPI mOpenApi;

    private Map<String,String> mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOpenApi = OpenAPI.initOpen(this);

    }

    public void login(View view) {
        mOpenApi.doAuthVerify(SHARE_MEDIA.SIAN, new IAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA shareMedia, Map<String, String> map) {
                mMap = map;
            }

            @Override
            public void onError(SHARE_MEDIA shareMedia, int errorCode, String errorMessage) {

            }

            @Override
            public void onCancel(SHARE_MEDIA shareMedia) {

            }
        });

    }

    public void getInfo(View view) {

        mOpenApi.getUserInfo(SHARE_MEDIA.SIAN, new IUserListener() {
            @Override
            public void onComplete(SHARE_MEDIA shareMedia, Map<String, String> map) {
                mMap = map;
            }

            @Override
            public void onError(SHARE_MEDIA shareMedia, int errorCode, String errorMessage) {

            }
        });


    }


    public void openShare(View view) {

        ShareMedia media = new ShareMedia();
        media.setMediaType(ShareMedia.SHARE_MUSIC);
//        media.setmMediaUrl("http://sc1.111ttt.com/2016/1/12/10/205102152511.mp4");
//        media.setMediaType(ShareMedia.SHARE_IMGE);
        media.setImageUrl("http://s4.sinaimg.cn/middle/5db29a99h70e7bb8abe33&690");
        ShareAPI shareAPI = new ShareAPI(this);
        shareAPI.withTitle("微信分享测试")
                .setPlatform(SHARE_MEDIA.SIAN)
                .withTargetUrl("http://sc1.111ttt.com/2016/1/12/10/205102152511.mp4")
                .withText("微信测试分享内容")
                .withMedia(media)
                .setShareListener(new IShareListener() {
                    @Override
                    public void onSuccess(SHARE_MEDIA shareMedia) {
                        toas("分享成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA shareMedia, int errorCode, String errorMessage) {
                        toas("分享失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA shareMedia) {
                        toas("取消分享");
                    }
                })
                .share();



    }

    private void toas(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 登录
     */
    public void loginSina() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
//        if (mSsoHandler != null) {
//            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
        OpenAPI.activityResultData(requestCode,resultCode,data);

    }
}
