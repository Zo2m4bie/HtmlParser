package com.htmlparser.span;

import android.text.TextPaint;
import android.text.style.CharacterStyle;

import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.span.type.CustomSpan;

import java.util.List;


public class BodySpan extends CharacterStyle implements CustomSpan {
    private List<BaseStyle> mCssStyleList;

    public List<BaseStyle> getStylesList() {
        return mCssStyleList;
    }

    public void setStylesList(List<BaseStyle> cssStyleList) {
        mCssStyleList = cssStyleList;
    }

    @Override
    public void updateDrawState(TextPaint tp) {

    }

}
