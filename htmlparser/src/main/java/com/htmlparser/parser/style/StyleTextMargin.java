package com.htmlparser.parser.style;

import android.text.style.CharacterStyle;
import android.text.style.LeadingMarginSpan;

import com.htmlparser.span.CustomLeadingMarginSpan;


public class StyleTextMargin extends BaseStyle implements ParagraphStyle {


    @Override
    public void setKey() {
        mKey = "margin-left";
    }

    @Override
    public void parseSpan(CharacterStyle style) {
    }

    @Override
    public void parseSpan(android.text.style.ParagraphStyle style) {
        //need set ignore false that would get leadingMargin work correct
        if(style instanceof CustomLeadingMarginSpan){
            ((CustomLeadingMarginSpan) style).setIgnoreMargin(false);
        }

        int margin = ((LeadingMarginSpan) style).getLeadingMargin(true);
        if (margin > 0) {
            setValue(margin);
        }
    }

    @Override
    public void setValue(String value) {
        if (!value.contains("px")) {
            value += "px";
        }
        super.setValue(value);
    }

    @Override
    public android.text.style.ParagraphStyle getAndroidParagraphSpan() {
        String marginStr = getValue();
        marginStr = marginStr.substring(0, mValue.indexOf("px"));
        int margin = Integer.parseInt(marginStr);
        LeadingMarginSpan marginSpan = new CustomLeadingMarginSpan(margin, margin);
        return marginSpan;
    }

    @Override
    public CharacterStyle getAndroidCharacterSpan() {
        return null;
    }

    public void setValue(int margin) {
        if (margin > -1) {
            setValue(String.valueOf(margin));
        }
    }
}
