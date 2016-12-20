package com.htmlparser.span;

import android.graphics.Typeface;
import android.os.Parcel;
import android.text.style.StyleSpan;

import com.htmlparser.span.type.CustomSpan;

public class ItalicSpan extends StyleSpan implements CustomSpan {

    public ItalicSpan(Parcel src) {
        super(src);
    }

    public ItalicSpan()
    {
        super(Typeface.ITALIC);
    }
}
