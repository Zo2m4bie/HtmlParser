package com.htmlparser.parser.style;

import android.text.style.ParagraphStyle;

import com.htmlparser.parser.BuilderHelper;
import com.htmlparser.parser.TextUtil;
import com.htmlparser.span.FontColorSpan;


public class StyleForegroundColor extends BaseStyle implements CharacterStyle {

    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{8}|[A-Fa-f0-9]{6})";

    @Override
    public void setKey() {
        mKey = "color";
    }

    @Override
    public void parseSpan(android.text.style.CharacterStyle style) {
        setValue(((FontColorSpan) style).getForegroundColor());
    }

    @Override
    public void parseSpan(ParagraphStyle style) {
    }


    public void setValue(int color) {
        setValue(TextUtil.toHtmlColor(color));
    }


    @Override
    public void setValue(String value) {
        if (value == null) {
            return;
        }
        if (value.contains(BuilderHelper.RGB_FORMAT)) {
            value = BuilderHelper.convertFromRgbToHex(value);
        }
        if (value.matches(HEX_PATTERN)) {
            super.setValue(value);
        }
    }

    @Override
    public ParagraphStyle getAndroidParagraphSpan() {
        return null;
    }

    @Override
    public android.text.style.CharacterStyle getAndroidCharacterSpan() {
        if(getValue() == null){
            return null;
        }
        FontColorSpan colorSpan = null;
        colorSpan = new FontColorSpan(TextUtil.fromHtmlColor(getValue(), false));
        return colorSpan;
    }
}
