package com.htmlparser.parser;

import android.content.Context;


public class ParserOptions {

    private int mMinFontSize;
    private int mMaxFontSize;
    private int mDefaultFontSize;

    public ParserOptions() {
    }

    public int getMinFontSize() {
        return mMinFontSize;
    }

    public int getMaxFontSize() {
        return mMaxFontSize;
    }

    public int getDefaultFontSize() {
        return mDefaultFontSize;
    }

    public void setFontRange(int minFontSize, int maxFontSize, int defaultFontSize) {
        mMinFontSize = minFontSize;
        mMaxFontSize = maxFontSize;
        mDefaultFontSize = defaultFontSize;
    }

}
