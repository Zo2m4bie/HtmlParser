package com.htmlparser.parser.style;

import android.graphics.Typeface;
import android.text.style.ParagraphStyle;

import com.htmlparser.parser.TypefaceUtil;
import com.htmlparser.span.CustomTypefaceSpan;

public class StyleFontFamily extends BaseStyle implements CharacterStyle {

    @Override
    public void setKey() {
        mKey = "font-family";
    }

    @Override
    public void parseSpan(android.text.style.CharacterStyle style) {
        setValue(((CustomTypefaceSpan) style).getFamily());
    }

    @Override
    public void parseSpan(ParagraphStyle style) {
    }

    @Override
    public void setValue(String value) {
        if (value.contains(",")) {
            value = value.split(",")[0];
        }
        //remove '' from font name
        value = value.replaceAll("[^a-zA-Z0-9 -]","");
        super.setValue(value);
    }

    @Override
    public ParagraphStyle getAndroidParagraphSpan() {
        return null;
    }

    @Override
    public android.text.style.CharacterStyle getAndroidCharacterSpan() {
        Typeface tfToSet = TypefaceUtil.getTypeFace(mValue);
        return new CustomTypefaceSpan(mValue, tfToSet);
    }


}
