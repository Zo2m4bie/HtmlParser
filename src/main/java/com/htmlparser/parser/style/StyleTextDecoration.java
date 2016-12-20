package com.htmlparser.parser.style;

import android.text.style.ParagraphStyle;
import android.text.style.StrikethroughSpan;

import com.htmlparser.span.CustomUnderlineSpan;


public class StyleTextDecoration extends BaseStyle implements CharacterStyle {

    @Override
    public void setKey() {
        mKey = "text-decoration";
    }

    @Override
    public void parseSpan(android.text.style.CharacterStyle style) {
        if (style instanceof CustomUnderlineSpan) {
            setValue(CssTextDecoration.UNDERLINE);
        } else if (style instanceof StrikethroughSpan) {
            setValue(CssTextDecoration.LINE_THROUGH);
        } else {
            setValue(CssTextDecoration.NONE);
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
        android.text.style.CharacterStyle style = null;
        if (CssTextDecoration.UNDERLINE.toString().equals(value)) {
            style = new CustomUnderlineSpan();
        } else if (CssTextDecoration.LINE_THROUGH.equals(value)) {
            style = new StrikethroughSpan();
        } else if (CssTextDecoration.OVERLINE.equals(value)){

        } else if(CssTextDecoration.NONE.equals(value)){

        }
        return style;
    }

    public void setValue(CssTextDecoration textDecoration) {
        switch (textDecoration) {
            case NONE:
                setValue("none");
                break;
            case UNDERLINE:
                setValue("underline");
                break;
            case OVERLINE:
                setValue("overline");
                break;
            case LINE_THROUGH:
                setValue("line-through");
                break;
        }
    }

    public static enum CssTextDecoration {
        NONE("none"), UNDERLINE("underline"), OVERLINE("overline"), LINE_THROUGH("line-through");

        private String name;

        CssTextDecoration(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
