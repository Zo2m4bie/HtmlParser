package com.htmlparser.span;

import android.os.Parcel;
import android.text.style.ForegroundColorSpan;

import com.htmlparser.span.type.CustomSpan;

public class FontColorSpan extends ForegroundColorSpan  implements CustomSpan {
    public FontColorSpan(int color) {
        super(color);
    }

    public FontColorSpan(Parcel src) {
        super(src);
    }
}
