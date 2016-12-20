package com.htmlparser.parser.style;

import android.text.style.CharacterStyle;

import com.htmlparser.span.type.NoMergeSpan;


public class StyleBackgroungImage extends BaseStyle implements ParagraphStyle, NoMergeSpan {
    @Override
    public void setKey() {
        mKey = KEY_BACKGROUND_IMAGE;
    }

    @Override
    public void parseSpan(CharacterStyle style) {

    }

    @Override
    public void parseSpan(android.text.style.ParagraphStyle style) {

    }

    @Override
    public android.text.style.ParagraphStyle getAndroidParagraphSpan() {
        return null;
    }

    @Override
    public CharacterStyle getAndroidCharacterSpan() {
        return null;
    }
}
