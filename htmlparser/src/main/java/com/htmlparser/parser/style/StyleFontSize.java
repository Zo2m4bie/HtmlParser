package com.htmlparser.parser.style;

import android.text.style.ParagraphStyle;
import android.util.Log;

import com.htmlparser.parser.ParserOptions;
import com.htmlparser.span.FontSizeSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StyleFontSize extends BaseStyle implements CharacterStyle {

    public static final String TAG = StyleFontSize.class.getName();

    private final Pattern mPattern = Pattern.compile("((\\d*)|(\\d+\\.?\\d+))(px|pt)");
    private int mDefaultSize;

    @Override
    public void readOptions(ParserOptions options) {
        mDefaultSize = options.getDefaultFontSize();
    }

    @Override
    public void setKey() {
        mKey = "font-size";
    }

    @Override
    public void parseSpan(android.text.style.CharacterStyle style) {
        setValue(((FontSizeSpan) style).getSize());
    }

    @Override
    public void parseSpan(ParagraphStyle style) {
    }

    public void setValue(int value) {
        setValue(String.valueOf(value));
    }

    @Override
    public void setValue(String value) {
        if (!value.contains("px") && !value.contains("pt")) {
            value += "px";
        }
        super.setValue(value);
    }

    @Override
    public ParagraphStyle getAndroidParagraphSpan() {
        return null;
    }

    @Override
    public android.text.style.CharacterStyle getAndroidCharacterSpan() {
        int sizeI = mDefaultSize;

        Matcher m = mPattern.matcher(mValue);
        if (m.find()) {
            String size = m.group(1);
            try {
                sizeI = size.contains(".") ? (int) Float.parseFloat(size) : Integer.decode(size);
            } catch (NumberFormatException e) {
                Log.e(TAG, "cant Parse FontSize. Use Default Instead");
                sizeI = mDefaultSize;
            }
        }
        FontSizeSpan sizeSpan = new FontSizeSpan(sizeI, true);
        return sizeSpan;
    }
}
