package com.htmlparser.parser.style;

import android.text.style.ParagraphStyle;

import com.htmlparser.parser.BuilderHelper;
import com.htmlparser.parser.TextUtil;
import com.htmlparser.span.FontHighlightSpan;


public class StyleBackgroundColor extends BaseStyle implements CharacterStyle {

    private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{8}|[A-Fa-f0-9]{6})";

    @Override
    public void setKey() {
        mKey = "background-color";
    }

    @Override
    public void parseSpan(android.text.style.CharacterStyle style) {
         setValue(((FontHighlightSpan) style).getBackgroundColor());
    }

    @Override
    public void parseSpan(ParagraphStyle style) { }


    public void setValue(int color) {
        setValue(TextUtil.toHtmlColor(color));
    }

    @Override
    public void setValue(String value) {
        if (value == null) {
            return;
        }
        value = value.trim();
        if(value.contains(BuilderHelper.RGB_FORMAT)){
            value = BuilderHelper.convertFromRgbToHex(value);
        }
        if(value.matches(HEX_PATTERN)){
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
        FontHighlightSpan span = new FontHighlightSpan(TextUtil.fromHtmlColor(getValue(), true));
        return span;
    }
}
