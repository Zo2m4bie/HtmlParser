package com.htmlparser.span;

import android.os.Parcel;
import android.text.style.StyleSpan;

import com.htmlparser.span.type.CustomSpan;

public class BoldSpan extends StyleSpan implements CustomSpan {

    public BoldSpan(Parcel src) {
        super(src);
    }

    public  BoldSpan() {
        super(android.graphics.Typeface.BOLD);
    }
}
