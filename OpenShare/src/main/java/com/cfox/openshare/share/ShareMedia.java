package com.cfox.openshare.share;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * <br/>************************************************
 * <br/>PROJECT_NAME : Hlsenior
 * <br/>PACKAGE_NAME : com.cfox.openshare.share
 * <br/>AUTHOR : Machao
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

    private String mMediaUrl;
    private int mMediaType;
    private ArrayList<String> mImageUrls;

    public ShareMedia() {
        mMediaType = SHARE_DEFAULT;
    }

    public ShareMedia(@NonNull String mediaUrl) {
        mMediaType = SHARE_DEFAULT;
        this.mMediaUrl = mediaUrl;
    }

    public ShareMedia(@NonNull String mediaUrl,@NonNull int mediaType) {
        this.mMediaUrl = mediaUrl;
        this.mMediaType = mediaType;
    }

    public ShareMedia(@NonNull String mediaUrl,@NonNull int mediaType, @NonNull String imageUrl) {
        this.mImageUrls.clear();
        this.mMediaUrl = mediaUrl;
        this.mMediaType = mediaType;
        this.addImageUrl(mediaUrl);
    }

    public ShareMedia(@NonNull String mMediaUrl,@NonNull int mMediaType,@NonNull ArrayList<String> mImageUrls) {
        this.mMediaUrl = mMediaUrl;
        this.mMediaType = mMediaType;
        this.mImageUrls = mImageUrls;
    }

    public String getMediaUrl() {
        return mMediaUrl;
    }

    public void setmMediaUrl(String mMediaUrl) {
        this.mMediaUrl = mMediaUrl;
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
        return mImageUrls.get(0);
    }

    private void addImageUrl(String imageUrl){

        if (mImageUrls == null){
            mImageUrls = new ArrayList<>();
        }
        mImageUrls.clear();
        mImageUrls.add(imageUrl);
    }
}
