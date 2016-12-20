package com.htmlparser.span;

import android.os.Parcel;
import android.text.style.URLSpan;

import com.htmlparser.span.type.CustomSpan;

public class CustomUrlSpan extends URLSpan implements CustomSpan {

    private String mTitle;

    public CustomUrlSpan(String url) {
        super(url);
    }

    public CustomUrlSpan(Parcel src) {
        super(src);
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }
}
