package com.htmlparser.parser.style;

import android.text.style.CharacterStyle;
import android.text.style.LeadingMarginSpan;
import android.text.style.ParagraphStyle;


import com.htmlparser.span.CustomLeadingMarginSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StyleMargin extends BaseStyle {

    public static final Pattern NUMBER_PTRN = Pattern.compile("^-?\\d*");
    public static final Pattern UNIT_PTRN = Pattern.compile("\\D*");

    // This margin can be represented in format "0 0 0 0" for "top right bottom left" relatively

    @Override
    public void setKey() {
        mKey = "margin";
    }

    @Override
    public void parseSpan(CharacterStyle style) {

    }

    @Override
    public void parseSpan(ParagraphStyle style) {
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
    public ParagraphStyle getAndroidParagraphSpan() {
        int leftMargin = getMarginFromString();
        LeadingMarginSpan marginSpan=null;
        if(leftMargin>0) {
            marginSpan = new CustomLeadingMarginSpan(leftMargin);
        }
        return marginSpan;
    }

    private int getMarginFromString() {
        String marginStr = getValue();
        String margins[] = marginStr.split("\\s");
        int leftMargin = 0;
        if (margins.length > 1) {
            marginStr = margins[3];
        } else {
            marginStr = margins[0];
        }
        Matcher m = NUMBER_PTRN.matcher(marginStr);
        if (m.find()) {
            String sizeStr = m.group();
            leftMargin = Integer.parseInt(sizeStr);
        }

        return leftMargin;
    }

    @Override
    public CharacterStyle getAndroidCharacterSpan() {
        return null;
    }

    @Override
    public void setValue(String value) {
        String[] values = value.split("\\s");
        int diff = 4 - values.length;
        //Considering it's left margin by default
        if(diff > 0){
            for(int j = 0; j < diff; j++){
                value = "0 "+value;
            }
        }
        super.setValue(value);
    }

    public void setValue(int margin) {
        if (margin > -1) {
            setValue(String.valueOf(margin));
        }
    }

    public int getMargin(){
        return getMarginFromString();
    }
}
