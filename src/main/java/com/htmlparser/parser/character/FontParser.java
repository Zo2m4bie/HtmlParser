package com.htmlparser.parser.character;

import android.graphics.Color;
import android.util.Log;


import com.htmlparser.HtmlElement;
import com.htmlparser.parser.BuilderHelper;
import com.htmlparser.parser.ParserOptions;
import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.parser.style.StyleFontFamily;
import com.htmlparser.parser.style.StyleFontSize;
import com.htmlparser.parser.style.StyleForegroundColor;

import java.util.HashMap;
import java.util.Map;

public class FontParser extends AbstractCharacterParser {

    private static final String TAG = FontParser.class.getName();

    public static final String ATTRIBUTE_FACE = "face";
    public static final String ATTRIBUTE_COLOR = "color";
    public static final String ATTRIBUTE_SIZE = "size";
    public static final String ATTRIBUTE_STYLE = "style";
    private static final int MAX_HTML_FONT_SIZE = 7;


    public FontParser() {
    }

    @Override
    public boolean canHandle(HtmlElement e) {
        return HtmlElement.FONT.equals(e);
    }

    @Override
    public void extractStylesFromAtts(HashMap<String, String> attributes, ParserOptions options) {

        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            BaseStyle cssStyle = null;
            String value = entry.getValue();
            if (ATTRIBUTE_COLOR.equals(entry.getKey())) {
                String hexColor = null;
                if (value.contains(BuilderHelper.RGB_FORMAT)) {
                    hexColor = BuilderHelper.convertFromRgbToHex(value);
                } else if (value.matches(BuilderHelper.HEX_PATTERN)) {
                    hexColor = value;
                }
                cssStyle = new StyleForegroundColor();
                if (hexColor == null) {
                    int color = Color.BLACK;
                    try {
                        color = Color.parseColor(value);
                    } catch (IllegalArgumentException ex) {
                        Log.e(TAG, ex.getLocalizedMessage());
                    }

                    ((StyleForegroundColor) cssStyle).setValue(color);
                } else {
                    cssStyle.setValue(hexColor);
                }

            } else if (ATTRIBUTE_SIZE.equals(entry.getKey())) {
                cssStyle = new StyleFontSize();
                int htmlFontSize = Integer.valueOf(entry.getValue());
                int androidFontSize = convertFontSizeFromHtml(htmlFontSize);
                ((StyleFontSize) cssStyle).setValue(androidFontSize);

            } else if (ATTRIBUTE_FACE.equals(entry.getKey())) {
                cssStyle = new StyleFontFamily();
                cssStyle.setValue(value);
            } else if (ATTRIBUTE_STYLE.equals(entry.getKey())) {
                String[] styles = value.split(";");
                if (styles == null || styles.length == 0) {
                    break;
                }
                for (String strStyle : styles) {
                    BaseStyle style = BaseStyle.parseFromString(strStyle);
                    addStyle(style);
                }
            }
            addStyle(cssStyle);
        }
    }

    /**
     * Convert size from 1-7 to corresponding size in px between mMaxSize and mMinSize
     *
     * @param size html size
     * @return px size for editor
     */
    private int convertFontSizeFromHtml(int size) {
        int result = size * 5;
        return result > 5 ? result : 10;
    }
}
