package com.htmlparser.parser.style;

import android.graphics.Typeface;
import android.text.style.ParagraphStyle;
import android.text.style.StyleSpan;

import com.htmlparser.span.ItalicSpan;

public class StyleFontStyle extends BaseStyle implements CharacterStyle {

    @Override
    public void setKey() {
        mKey = "font-style";
    }

    @Override
    public void parseSpan(android.text.style.CharacterStyle style) {
        if (style instanceof ItalicSpan) {
            setValue(CssFontStyle.ITALIC);
        } else {
            setValue(CssFontStyle.NORMAL);
        }
    }

    @Override
    public void parseSpan(ParagraphStyle style) {
    }

    @Override
    public ParagraphStyle getAndroidParagraphSpan() {
        return null;
    }

    @Override
    public android.text.style.CharacterStyle getAndroidCharacterSpan() {
        String value = getValue();
        StyleSpan styleSpan = null;
        if ("italic".equalsIgnoreCase(value)) {
            styleSpan = new ItalicSpan();
        } else if ("normal".equalsIgnoreCase(value)) {
            styleSpan = new StyleSpan(Typeface.NORMAL);
        } else if ("inherit".equalsIgnoreCase(value)) {
            styleSpan = new StyleSpan(Typeface.NORMAL);
        } else if ("oblique".equalsIgnoreCase(value)) {
            styleSpan = new StyleSpan(Typeface.NORMAL);
        }
        return styleSpan;
    }

    public void setValue(CssFontStyle style) {
        switch (style) {
            case ITALIC:
                setValue("italic");
                break;
            case NORMAL:
                setValue("normal");
                break;
            case INHERIT:
                setValue("inherit");
                break;
            case OBLIQUE:
                setValue("oblique");
                break;
        }
    }

    public enum CssFontStyle {
        ITALIC, NORMAL, INHERIT, OBLIQUE
    }
}
