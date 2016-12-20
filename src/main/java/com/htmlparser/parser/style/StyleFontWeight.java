package com.htmlparser.parser.style;

import android.graphics.Typeface;
import android.text.style.ParagraphStyle;
import android.text.style.StyleSpan;

import com.htmlparser.span.BoldSpan;


public class StyleFontWeight extends BaseStyle implements CharacterStyle {

    @Override
    public void setKey() {
        mKey = "font-weight";
    }

    @Override
    public void parseSpan(android.text.style.CharacterStyle style) {
        if (style instanceof BoldSpan) {
            setValue(CssFontWeight.BOLD);
        } else {
            setValue(CssFontWeight.NORMAL);
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
        if ("bold".equalsIgnoreCase(value)) {
            styleSpan = new BoldSpan();
        } else if ("normal".equalsIgnoreCase(value)) {
            styleSpan = new StyleSpan(Typeface.NORMAL);
        } else if ("bolder".equalsIgnoreCase(value)) {
            styleSpan = new BoldSpan();
        } else if ("lighter".equalsIgnoreCase(value)) {
            styleSpan = new StyleSpan(Typeface.NORMAL);
        } else if ("inherit".equalsIgnoreCase(value)) {
            styleSpan = new StyleSpan(Typeface.NORMAL);
        }
        return styleSpan;
    }


    public void setValue(CssFontWeight weight) {
        switch (weight) {
            case BOLD:
                setValue("bold");
                break;
            case NORMAL:
                setValue("normal");
                break;
            case BOLDER:
                setValue("bolder");
                break;
            case LIGHTER:
                setValue("lighter");
                break;
            case INHERIT:
                setValue("inherit");
                break;
        }

    }

    public enum CssFontWeight {
        BOLD, NORMAL, BOLDER, LIGHTER, INHERIT
    }
}
