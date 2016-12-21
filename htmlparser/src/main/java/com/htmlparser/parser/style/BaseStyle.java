package com.htmlparser.parser.style;

import android.text.style.CharacterStyle;
import android.text.style.ParagraphStyle;

import com.htmlparser.parser.ParserOptions;

public abstract class BaseStyle {


    public static final String KEY_BACKGROUND_COLOR = "background-color";
    public static final String KEY_FOREGROUND_COLOR = "color";
    public static final String KEY_FONT_FAMILY = "font-family";
    public static final String KEY_FONT_SIZE = "font-size";
    public static final String KEY_FONT_STYLE = "font-style";
    public static final String KEY_FONT_WEIGHT = "font-weight";
    public static final String KEY_TEXT_ALIGN = "text-align";
    public static final String KEY_TEXT_DECORATION = "text-decoration";
    public static final String KEY_TEXT_MARGIN = "margin-left";
    public static final String KEY_MARGIN = "margin";
    public static final String KEY_BACKGROUND_IMAGE = "background-image";
    public static final String KEY_BACKGROUND_IMAGE_INDEX = "background-image-index";
    public static final String KEY_BACKGROUND_COLOR_INDEX = "background-color-index";

    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_DEFAULT = 0;


    protected String mKey;
    protected String mValue;
    //1 - don't erase, 0 - erase;
    private int mPriority = PRIORITY_DEFAULT;


    public void setPriority(int priority){
        mPriority = priority;
    }

    public int getPriority(){
        return mPriority;
    }

    public BaseStyle() {
        setKey();
    }

    public abstract void setKey();


    public abstract void parseSpan(CharacterStyle style);

    public abstract void parseSpan(ParagraphStyle style);

    public void readOptions(ParserOptions options) {

    }
    public void setValue(String value) {
        this.mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseStyle cssStyle = (BaseStyle) o;

        if (mValue != null ? !mValue.equals(cssStyle.mValue) : cssStyle.mValue != null)
            return false;


        return true;
    }

    @Override
    public int hashCode() {
        return mValue != null ? mValue.hashCode() : 0;
    }

    @Override
    public String toString() {
        return mValue == null ? "" : mKey + ":" + mValue + ";";
    }


    /**
     * @param style string in format key:value
     */
    public static BaseStyle parseFromString(String style) {
        if (style == null || !style.contains(":")) {
            return null;
        }
        String[] keyValue = new String[2];
        keyValue[0] = style.substring(0, style.indexOf(":")).trim();
        keyValue[1] = style.substring(style.indexOf(":") + 1, style.length()).trim();
        BaseStyle cssStyle = getStyleForKey(keyValue[0]);
        if (cssStyle != null) {
            cssStyle.setValue(keyValue[1]);
        }
        return cssStyle;
    }


    public static BaseStyle getStyleForKey(String key) {
        BaseStyle style;
        if (KEY_BACKGROUND_COLOR.equals(key)) {
            style = new StyleBackgroundColor();
        } else if (KEY_FOREGROUND_COLOR.equals(key)) {
            style = new StyleForegroundColor();
        } else if (KEY_FONT_FAMILY.equals(key)) {
            style = new StyleFontFamily();
        } else if (KEY_FONT_SIZE.equals(key)) {
            style = new StyleFontSize();
        } else if (KEY_FONT_STYLE.equals(key)) {
            style = new StyleFontStyle();
        } else if (KEY_FONT_WEIGHT.equals(key)) {
            style = new StyleFontWeight();
        } else if (KEY_TEXT_ALIGN.equals(key)) {
            style = new StyleTextAlign();
        } else if (KEY_TEXT_DECORATION.equals(key)) {
            style = new StyleTextDecoration();
        } else if (KEY_TEXT_MARGIN.equals(key)) {
            style = new StyleTextMargin();
        } else if (KEY_BACKGROUND_IMAGE.equals(key)) {
            style = new StyleBackgroungImage();
        } else if (KEY_BACKGROUND_IMAGE_INDEX.equals(key)) {
            style = new StyleBackgroungImageIndex();
        } else if (KEY_BACKGROUND_COLOR_INDEX.equals(key)) {
            style = new StyleBackgroundColorIndex();
        } else if (KEY_MARGIN.equals(key)) {
            style = new StyleMargin();
        } else {
            style = null;
        }

        return style;
    }

    public abstract ParagraphStyle getAndroidParagraphSpan();

    public abstract CharacterStyle getAndroidCharacterSpan();

}
