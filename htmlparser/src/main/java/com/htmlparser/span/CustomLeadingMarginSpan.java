package com.htmlparser.span;

import android.os.Parcel;
import android.text.style.LeadingMarginSpan;

import com.htmlparser.span.type.CustomSpan;

public class CustomLeadingMarginSpan extends LeadingMarginSpan.Standard implements CustomSpan {

    protected boolean mIgnoreMargin = false;

    public CustomLeadingMarginSpan(int first, int rest) {
        super(first, rest);
    }

    public CustomLeadingMarginSpan(int every) {
        super(every);
    }

    public CustomLeadingMarginSpan(Parcel src) {
        super(src);
    }

    @Override
    public int getLeadingMargin(boolean first) {
        int result = 0;
        if (!mIgnoreMargin) {
            result = super.getLeadingMargin(first);
        }
        return result;
    }


    public void setIgnoreMargin(boolean ignoreMargin){
        mIgnoreMargin = ignoreMargin;
    }

    public boolean isIgnoreMargin(){
        return mIgnoreMargin;
    }
}
