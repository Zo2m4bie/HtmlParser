package com.htmlparser.parser.paragraph;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.LeadingMarginSpan;
import android.text.style.ParagraphStyle;

import com.htmlparser.HtmlElement;
import com.htmlparser.parser.style.BaseStyle;

import java.util.List;

public class LineBreakParser extends AbstractParagraphParser {
    @Override
    public boolean canHandle(HtmlElement e) {
        return HtmlElement.BR.equals(e);
    }

    @Override
    public Spanned parse() {
        SpannableStringBuilder ssb = getSpannableStringBuilder();
        appendText("\n");
        List<BaseStyle> styleList = getCssStyles();
        if (styleList != null) {
            for (BaseStyle style : styleList) {
                ParagraphStyle paragraphSpan = style.getAndroidParagraphSpan();
                CharacterStyle characterSpan = style.getAndroidCharacterSpan();
                if (characterSpan != null) {
                    ssb.setSpan(characterSpan,0,ssb.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                }
                if (paragraphSpan != null && !(paragraphSpan instanceof LeadingMarginSpan)) {
                    checkAlignSpanAndList(ssb, paragraphSpan);
                    ssb.setSpan(paragraphSpan, 0,
                            ssb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    ignoreMarginForCenterALign(ssb, paragraphSpan);
                }
            }
        }
        finish();
        return ssb;
    }
}
