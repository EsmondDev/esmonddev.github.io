package com.w3code.library.theme;

public class Theme {

    public int mId;
    public final int mAccentColor;
    public final int mPrimaryColor;
    public final int mPrimaryDarkColor;
    public Theme(int primaryColor, int primaryDarkColor, int accentColor) {
        this.mPrimaryColor = primaryColor;
        this.mPrimaryDarkColor = primaryDarkColor;
        this.mAccentColor = accentColor;
    }
    public Theme(int id , int primaryColor, int primaryDarkColor, int accentColor) {
        this.mId = id;
        this.mPrimaryColor = primaryColor;
        this.mPrimaryDarkColor = primaryDarkColor;
        this.mAccentColor = accentColor;
    }
    public int getId() {
        return mId;
    }
    public int getPrimaryColor() {
        return mPrimaryColor;
    }
    public int getPrimaryDarkColor() {
        return mPrimaryDarkColor;
    }
    public int getAccentColor() {
        return mAccentColor;
    }
}