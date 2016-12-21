package com.htmlparser.span;

import android.os.Parcel;
import android.text.style.BackgroundColorSpan;

import com.htmlparser.span.type.CustomSpan;

public class FontHighlightSpan extends BackgroundColorSpan implements CustomSpan{

    public FontHighlightSpan(int color) {
        super(color);
    }

    public FontHighlightSpan(Parcel src) {
        super(src);
    }
}
