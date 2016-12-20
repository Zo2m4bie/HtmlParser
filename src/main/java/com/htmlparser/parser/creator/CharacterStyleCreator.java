package com.htmlparser.parser.creator;

import android.text.style.CharacterStyle;
import android.text.style.StrikethroughSpan;

import com.htmlparser.HtmlElement;
import com.htmlparser.parser.ParserOptions;
import com.htmlparser.parser.tagclass.BaseTagClass;
import com.htmlparser.parser.tagclass.TagSpan;
import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.parser.style.StyleBackgroundColor;
import com.htmlparser.parser.style.StyleFontFamily;
import com.htmlparser.parser.style.StyleFontSize;
import com.htmlparser.parser.style.StyleFontStyle;
import com.htmlparser.parser.style.StyleFontWeight;
import com.htmlparser.parser.style.StyleForegroundColor;
import com.htmlparser.parser.style.StyleTextDecoration;
import com.htmlparser.span.BoldSpan;
import com.htmlparser.span.CustomTypefaceSpan;
import com.htmlparser.span.FontColorSpan;
import com.htmlparser.span.FontHighlightSpan;
import com.htmlparser.span.FontSizeSpan;
import com.htmlparser.span.ItalicSpan;
import com.htmlparser.span.CustomUnderlineSpan;


public class CharacterStyleCreator extends StyleClassCreator<CharacterStyle> {

    private final ParserOptions mOptions;

    public CharacterStyleCreator(ParserOptions options) {
        mOptions = options;
    }

    @Override
    public BaseTagClass create(HtmlElement e, String className) {
        return new TagSpan(className);
    }

    @Override
    public BaseStyle[] create(CharacterStyle style) {
        BaseStyle[] cssStyle = new BaseStyle[1];
        if (style instanceof BoldSpan) {
            cssStyle[0] = new StyleFontWeight();
        } else if (style instanceof ItalicSpan) {
            cssStyle[0] = new StyleFontStyle();
        } else if (style instanceof CustomUnderlineSpan || style instanceof StrikethroughSpan) {
            cssStyle[0] = new StyleTextDecoration();
        } else if (style instanceof CustomTypefaceSpan) {
            cssStyle[0] = new StyleFontFamily();
        } else if (style instanceof FontSizeSpan) {
            cssStyle[0] = new StyleFontSize();
        } else if (style instanceof FontColorSpan) {
            cssStyle[0] = new StyleForegroundColor();
        } else if (style instanceof FontHighlightSpan) {
            cssStyle[0] = new StyleBackgroundColor();
        }
        if (cssStyle[0] != null) {
            cssStyle[0].readOptions(mOptions);
            cssStyle[0].parseSpan(style);
        }
        return cssStyle;
    }
}
