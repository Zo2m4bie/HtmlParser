package com.htmlparser.span;

import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;

import com.htmlparser.span.type.CustomSpan;

public class AlignmentSpan implements android.text.style.AlignmentSpan, ParcelableSpan, CustomSpan {
    private final TextAlignment mAlignment;

    public AlignmentSpan(TextAlignment align) {
        mAlignment = align;
    }

    public AlignmentSpan(Parcel src) {
        mAlignment = TextAlignment.valueOf(src.readString());
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAlignment.name());
    }

    public Layout.Alignment getAlignment() {
        Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
        switch (mAlignment) {
            case RIGHT:
                alignment = Layout.Alignment.ALIGN_OPPOSITE;
                break;
            case CENTER:
                alignment = Layout.Alignment.ALIGN_CENTER;
                break;
        }
        return alignment;
    }

    /**
     * Returns our custom alignment for CustomEditText
     *
     * @return
     */
    public TextAlignment getTextAlignment() {
        return mAlignment;
    }

    @Override
    public int getSpanTypeId() {
        return 0;
    }
}
