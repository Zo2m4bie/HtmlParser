package com.htmlparser.span;

import android.os.Parcel;
import android.text.style.AbsoluteSizeSpan;

import com.htmlparser.span.type.CustomSpan;

public class FontSizeSpan extends AbsoluteSizeSpan implements CustomSpan {
    public FontSizeSpan(int size) {
        super(size);
    }

    public FontSizeSpan(int size, boolean dip) {
        super(size, dip);
    }

    public FontSizeSpan(Parcel src) {
        super(src);
    }
}
