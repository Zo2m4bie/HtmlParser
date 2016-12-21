package com.htmlparser.parser;

import com.htmlparser.StyleDoc;

public class StyleBuilder {
    //p{}
    private static final int MIN_LENGTH = 3;
    private StringBuilder mStringBuilder;

    public void appendStylesText(String text) {
        if (mStringBuilder == null) {
            mStringBuilder = new StringBuilder();
        }
        mStringBuilder.append(text);
    }

    public StyleDoc build(ParserOptions options) {
        if (mStringBuilder!=null && mStringBuilder.length() > MIN_LENGTH) {
            return StyleDoc.createFromText(mStringBuilder.toString(), options);
        } else {
            return new StyleDoc();
        }
    }
}