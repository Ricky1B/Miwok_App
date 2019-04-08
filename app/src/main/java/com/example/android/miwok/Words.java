package com.example.android.miwok;

public class Words {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private  int mImageId =NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED =-1;
    private int mAudioResourceId;

    public Words(String defaultTranslation, String miwokTranslation,int audioResourcId ){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourcId;

    }

    public Words(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourcId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageId = imageResourceId;
        mAudioResourceId = audioResourcId;
    }

    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }

    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    public int getImageID(){
       return mImageId;
    }
//returns whether or not there is an image in the word
    public boolean hasImage(){
        return mImageId != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}
