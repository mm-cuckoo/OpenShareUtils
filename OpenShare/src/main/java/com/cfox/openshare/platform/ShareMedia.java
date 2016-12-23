package com.cfox.openshare.platform;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : OpenShare
 * <br/>PACKAGE_NAME : com.cfox.openshare.platform
 * <br/>AUTHOR : CFOX
 * <br/>MSG :
 * <br/>************************************************
 */
public class ShareMedia {

    private static final String TAG = "ShareMedia";

    public static final int SHARE_DEFAULT   =   0x001;
    public static final int SHARE_TEXT      =   0x002;
    public static final int SHARE_IMGE      =   0x003;
    public static final int SHARE_VIDEO     =   0x004;
    public static final int SHARE_MUSIC     =   0x005;

    private int mMediaType;                     //类型 SHARE_TEXT 等
    private ArrayList<String> mImageUrls;       //分享的图片，如果是单张，去第 0 个
    private int mImageRes = -1;

    public ShareMedia() {
        mMediaType = SHARE_DEFAULT;
    }

    public ShareMedia(@NonNull int mediaType) {
        this.mMediaType = mediaType;
    }

    public ShareMedia(@NonNull int mediaType, @NonNull String imageUrl) {
        this.mImageUrls.clear();
        this.mMediaType = mediaType;
        this.addImageUrl(imageUrl);
    }

    public ShareMedia(@NonNull int mMediaType,@NonNull ArrayList<String> mImageUrls) {
        this.mMediaType = mMediaType;
        this.mImageUrls = mImageUrls;
    }

    public ShareMedia( @NonNull int mMediaType, @NonNull int mImageRes) {
        this.mMediaType = mMediaType;
        this.mImageRes = mImageRes;
    }

    public int getMediaType() {
        return mMediaType;
    }

    public void setMediaType(int mMediaType) {
        this.mMediaType = mMediaType;
    }

    public ArrayList<String> getImageUrls() {
        return mImageUrls;
    }

    public void setImageUrls(ArrayList<String> mImageUrls) {
        this.mImageUrls = mImageUrls;
    }

    public void setImageUrl(String ImageUrl) {
        this.addImageUrl(ImageUrl);
    }

    public String getImageUrl() {
        if (mImageUrls == null)
            return null;
        return mImageUrls.get(0);
    }

    private void addImageUrl(String imageUrl){

        if (mImageUrls == null){
            mImageUrls = new ArrayList<>();
        }
        mImageUrls.clear();
        mImageUrls.add(imageUrl);
    }

    public int getmImageRes() {
        return mImageRes;
    }

    public void setmImageRes(int mImageRes) {
        this.mImageRes = mImageRes;
    }
}
