package com.htmlparser.parser;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;


import com.htmlparser.HtmlElement;
import com.htmlparser.parser.paragraph.AbstractParagraphParser;
import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.span.BodySpan;

import java.util.List;

public class BodyParser extends AbstractParagraphParser {

    @Override
    public Spanned parse() {
        List<BaseStyle> styleList = getCssStyles();
        if (styleList != null) {
            SpannableStringBuilder stringBuilder = getSpannableStringBuilder();
            //Apply body formatting to all other text
            for (BaseStyle style : styleList) {
                CharacterStyle characterSpan = style.getAndroidCharacterSpan();
                if (characterSpan != null) {
                    setCharSpan(stringBuilder, style);
                }
            }
            BodySpan bodySpan = new BodySpan();
            bodySpan.setStylesList(styleList);
            stringBuilder.setSpan(bodySpan, 0, 0, Spanned.SPAN_MARK_MARK);
        }
        finish();
        return getSpannableStringBuilder();
    }


    @Override
    public boolean canHandle(HtmlElement e) {
        return HtmlElement.BODY.equals(e);
    }

}
