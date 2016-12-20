package com.htmlparser.parser.character;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;

import com.htmlparser.parser.AbstractParser;
import com.htmlparser.parser.TextUtil;
import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.span.SpanContainer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCharacterParser extends AbstractParser {

    @Override
    public Spanned parse() {
        List<BaseStyle> styleList = getCssStyles();
        SpannableStringBuilder stringBuilder = getSpannableStringBuilder();
        if (styleList == null) {
            finish();
            return stringBuilder;
        }
        for (BaseStyle style : styleList) {
            CharacterStyle characterSpan = style.getAndroidCharacterSpan();
            if (characterSpan == null) {
                continue;
            }
            if (isSpanExists(characterSpan.getClass()) && style.getPriority() != BaseStyle.PRIORITY_HIGH) {
                appendSpanToExistingOne(characterSpan, style);
            } else {
                int flag = (stringBuilder.length() == 0) ? Spanned.SPAN_MARK_MARK : Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
                stringBuilder.setSpan(characterSpan, 0, stringBuilder.length(), flag);
            }
        }

        finish();
        return stringBuilder;
    }

    private boolean isSpanExists(Class<? extends Object> clazz) {
        final SpannableStringBuilder ssb = getSpannableStringBuilder();
        Object[] spans = ssb.getSpans(0, ssb.length(), clazz);
        return spans != null && spans.length > 0;
    }

    private void appendSpanToExistingOne(CharacterStyle clazz, BaseStyle etalon) {
        final SpannableStringBuilder ssb = getSpannableStringBuilder();
        Object[] spans = ssb.getSpans(0, ssb.length(), clazz.getClass());
        if (spans == null || spans.length == 0) {
            return;
        }
        ArrayList<SpanContainer> list = new ArrayList<SpanContainer>();
        for (Object span : spans) {
            list.add(new SpanContainer(ssb.getSpanStart(span), ssb.getSpanEnd(span), span));
        }
        TextUtil.sortStyleListForStartIndex(list);

        if (list.size() == 1) {
            SpanContainer container = list.get(0);
            if (container.getStart() != 0) {
                ssb.setSpan(clazz, 0, container.getStart(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (container.getEnd() != ssb.length()) {
                ssb.setSpan(clazz, container.getEnd(), ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else {
            for (int i = 0; i < list.size() - 1; i++) {
                SpanContainer container = list.get(i);
                SpanContainer nextContainer = list.get(i + 1);
                if (i == 0) {
                    if (container.getStart() != 0) {
                        ssb.setSpan(etalon.getAndroidCharacterSpan(), 0, container.getStart(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                if (container.getEnd() < nextContainer.getStart()) {
                    ssb.setSpan(etalon.getAndroidCharacterSpan(), container.getEnd(), nextContainer.getStart(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (i == list.size() - 2) { //because for go list.size() - 1
                    if (nextContainer.getEnd() != ssb.length()) {
                        ssb.setSpan(etalon.getAndroidCharacterSpan(), nextContainer.getEnd(), ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }
    }


}
