package com.htmlparser;

import android.text.Editable;
import android.text.Spanned;

import com.htmlparser.builder.HtmlBuilder;
import com.htmlparser.parser.HtmlParser;

public class HTML {

    public static Spanned fromHtml(String html) {
        HtmlParser parser = new HtmlParser();
        return parser.parse(html);
    }

    public static String toHtml(Spanned spanned) {
        HtmlBuilder htmlBuilder = new HtmlBuilder();
        return htmlBuilder.build(spanned);
    }
}
