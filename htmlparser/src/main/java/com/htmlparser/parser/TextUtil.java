package com.htmlparser.parser;

import android.graphics.Color;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;
import android.util.Log;

import com.htmlparser.span.CustomLeadingMarginSpan;
import com.htmlparser.span.SpanContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TextUtil {

    public static final String HTML_COLOR_TRANSPARENT = "#00ffffff";
    private static final String DEFAULT_ALPHA_CHANNEL = "ff";
    private static final String TAG = TextUtil.class.getName();


    public static String toHtmlColor(int color) {
        if (color == Color.TRANSPARENT) {
            return HTML_COLOR_TRANSPARENT;
        }
        String res = Integer.toHexString(color + 0x01000000);//getHexColor(color);

        // Add necessary zeros at the beginning if needed
        while (res.length() < 6) {
            res = "0" + res;
        }
        //remove transparency for desktop
        if (res.length() == 8) {
            res = res.substring(2);
        }
        return "#" + res;
    }

    /**
     * Converts a specified HTML color value to some int color value
     *
     * @param colorString
     * @return -1 in case if the supplied color string can not be converted for any reason
     */
    public static int fromHtmlColor(String colorString, boolean restoreTransparency) {
        int res = -1;
        //No alpha channel
        if (restoreTransparency && colorString.length() == 7) {
            colorString = "#" + DEFAULT_ALPHA_CHANNEL + colorString.substring(1);
        }
        if (colorString.toLowerCase().contains(HTML_COLOR_TRANSPARENT)) {
            res = Color.TRANSPARENT;
        } else {
            try {
                res = Color.parseColor(colorString.toUpperCase());// | 0xFF000000;
            } catch (IllegalArgumentException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (NullPointerException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }


        return res;
    }

    public static void sortStyleListForStartIndex(List<SpanContainer> list) {
        Comparator<SpanContainer> comparator = new Comparator<SpanContainer>() {
            public int compare(SpanContainer o1, SpanContainer o2) {
                return o1.getStart() - o2.getStart();
            }
        };

        Collections.sort(list, comparator);
    }

    public static Spanned[] getParagraphsForSave(Spanned text) {
        List<Spanned> spanneds = new ArrayList<Spanned>();
        int paragraphEnd;
        for (int i = 0; i < text.length(); i = ++paragraphEnd) {
            paragraphEnd = TextUtils.indexOf(text, '\n', i, text.length());
            if (paragraphEnd == -1) {
                paragraphEnd = text.length();
            }
            spanneds.add((Spanned) (text.subSequence(i, paragraphEnd)));
        }
        Spanned[] resultArray = new Spanned[spanneds.size()];
        spanneds.toArray(resultArray);
        return resultArray;
    }

    public static void ignoreMargin(Spannable str, int start, int end, boolean ignoreMargin) {
        LeadingMarginSpan[] spans = str.getSpans(start, end, LeadingMarginSpan.class);
        for (LeadingMarginSpan span : spans) {
            if (span instanceof CustomLeadingMarginSpan) {
                ((CustomLeadingMarginSpan) span).setIgnoreMargin(ignoreMargin);
            }
        }
    }

}
