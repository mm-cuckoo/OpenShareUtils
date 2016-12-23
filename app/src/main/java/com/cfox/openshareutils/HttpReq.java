package com.cfox.openshareutils;

import android.graphics.Bitmap;

import com.cfox.openshare.http.IBitmapCallBack;
import com.cfox.openshare.http.IHttpRequest;
import com.cfox.openshare.http.IStringCallBack;
import com.cfox.openshare.http.ReqBuild;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

public class HttpReq implements IHttpRequest {
    @Override
    public void requestToSting(ReqBuild reqBuild, final IStringCallBack callBack) {
        GetBuilder getBuilder = OkHttpUtils.get();
        Map<String, String> params = reqBuild.params;
        getBuilder.url(reqBuild.reqUrl);
        for (String key : params.keySet()) {
            getBuilder.addParams(key, params.get(key));
        }
        getBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        callBack.onError(i, e.getMessage());
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        callBack.onSuccess(i, s);
                    }
                });
    }

    @Override
    public void requestToBitmap(String reqUrl, final IBitmapCallBack callBack) {
        OkHttpUtils.get().url(reqUrl).build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        callBack.onError(i,e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int i) {
                        callBack.onSuccess(i,bitmap);
                    }
                });
    }
}
