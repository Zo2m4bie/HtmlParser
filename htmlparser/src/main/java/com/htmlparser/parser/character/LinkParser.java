package com.htmlparser.parser.character;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;

import com.htmlparser.HtmlElement;
import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.parser.style.StyleFontSize;
import com.htmlparser.span.CustomUrlSpan;

import java.util.List;

public class LinkParser extends AbstractCharacterParser {
    private static final String KEY_ATTR_HREF = "href";
    private static final String KEY_ATTR_TITLE = "title";

    @Override
    public boolean canHandle(HtmlElement e) {
        return HtmlElement.A.equals(e);
    }

    @Override
    public Spanned parse() {
        SpannableStringBuilder spannableStringBuilder = getSpannableStringBuilder();
        String url = getAttributeValue(KEY_ATTR_HREF);
        String title = getAttributeValue(KEY_ATTR_TITLE);
        CustomUrlSpan urlSpan = new CustomUrlSpan(url);
        urlSpan.setTitle(title);

        List<BaseStyle> styles = getCssStyles();
        if(styles != null) {
            for (BaseStyle style : styles) {
                if (style instanceof StyleFontSize) {
                    CharacterStyle span = style.getAndroidCharacterSpan();
                    spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        spannableStringBuilder.setSpan(urlSpan, 0, spannableStringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        finish();
        return spannableStringBuilder;
    }
}
