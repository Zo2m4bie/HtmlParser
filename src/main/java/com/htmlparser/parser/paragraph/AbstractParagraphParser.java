package com.htmlparser.parser.paragraph;

import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.LeadingMarginSpan;
import android.text.style.ParagraphStyle;


import com.htmlparser.parser.AbstractParser;
import com.htmlparser.parser.TextUtil;
import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.span.AlignmentSpan;
import com.htmlparser.span.CustomLeadingMarginSpan;

import java.util.List;

public abstract class AbstractParagraphParser extends AbstractParser {

    @Override
    public Spanned parse() {
        List<BaseStyle> styleList = getCssStyles();
        SpannableStringBuilder stringBuilder = getSpannableStringBuilder();

        CharacterStyle[] spans = stringBuilder.getSpans(stringBuilder.length(), stringBuilder.length(), CharacterStyle.class);

        if (!paragraphEndSymbolExist()) {
            appendText("\n");
            expandCharSpansOnPosition(stringBuilder, spans, stringBuilder.length());
        }

        spans = stringBuilder.getSpans(0, 0, CharacterStyle.class);
        if (styleList != null) {
            for (BaseStyle style : styleList) {
                ParagraphStyle paragraphSpan = style.getAndroidParagraphSpan();
                CharacterStyle characterSpan = style.getAndroidCharacterSpan();
                if (characterSpan != null) {
                    setCharSpan(stringBuilder, style);
                }
                if (paragraphSpan != null) {
                    checkAlignSpanAndList(stringBuilder, paragraphSpan);
                    Object[] existingSpans = stringBuilder.getSpans(0, stringBuilder.length(), paragraphSpan.getClass());

                    //if there was no margin span before - add invisible symbol and stratch char spans
                    if (!(paragraphSpan instanceof CustomLeadingMarginSpan) // if it isn't indent
                            && paragraphSpan instanceof LeadingMarginSpan && existingSpans.length == 0) {
                        addInvisibleSymbol(1, stringBuilder);
                        expandCharSpansOnPosition(stringBuilder, spans, 0);
                    }
                    //if any paragraph span already set - dont set it againg or change.
                    if (existingSpans.length == 0) {
                        stringBuilder.setSpan(paragraphSpan, 0,
                                stringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }

        finish();
        return stringBuilder;
    }

    /**
     * Need if there is align span in spannable.
     */
    protected void checkAlignSpanAndList(SpannableStringBuilder ssb, ParagraphStyle paragraphSpan) {
        if (paragraphSpan instanceof LeadingMarginSpan) {
            AlignmentSpan[] spans = ssb.getSpans(0, ssb.length(), AlignmentSpan.class);
            if (spans.length > 0) {
                ignoreMarginForCenterALign(ssb, spans[0]);
            }
        }
    }

    /**
     * Ignore margin if current align is center align
     */
    protected void ignoreMarginForCenterALign(SpannableStringBuilder ssb, ParagraphStyle paragraphSpan) {
        if (paragraphSpan instanceof AlignmentSpan
                && ((AlignmentSpan) paragraphSpan).getAlignment() == Layout.Alignment.ALIGN_CENTER) {
            //need ignore to all spans
            TextUtil.ignoreMargin(ssb, 0, ssb.length(), true);
        }
    }

    protected void expandCharSpansOnPosition(SpannableStringBuilder stringBuilder, CharacterStyle[] spans, int position) {
        //continue style span on \n later
        for (CharacterStyle span : spans) {

            int start = stringBuilder.getSpanStart(span);
            int end = stringBuilder.getSpanEnd(span);
            if (position < start) {
                start = position;
            } else if (position > end) {
                end = position;
            }
            stringBuilder.removeSpan(span);
            stringBuilder.setSpan(span, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

    }

    protected void setCharSpan(SpannableStringBuilder ssb, BaseStyle style) {
        List<int[]> textRanges = getSimpleTextRanges();
        if (textRanges == null) {
            return;
        }
        for (int[] range : textRanges) {
            ssb.setSpan(style.getAndroidCharacterSpan(), range[0], range[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
