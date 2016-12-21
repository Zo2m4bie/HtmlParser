package com.htmlparser.builder.section;

import com.htmlparser.span.CustomUrlSpan;

public class LinkSection extends Section {

    private CustomUrlSpan mCustomUrlSpan;

    public LinkSection(int start, int end, CustomUrlSpan customUrlSpan) {
        super(start, end);
        mCustomUrlSpan = customUrlSpan;
    }

    public CustomUrlSpan getCustomUrlSpan() {
        return mCustomUrlSpan;
    }
}
