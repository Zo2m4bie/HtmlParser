package com.htmlparser.parser.creator;

import android.text.style.AlignmentSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.ParagraphStyle;

import com.htmlparser.HtmlElement;
import com.htmlparser.parser.tagclass.BaseTagClass;
import com.htmlparser.parser.tagclass.TagDiv;
import com.htmlparser.parser.tagclass.TagListItem;
import com.htmlparser.parser.tagclass.TagParagraph;
import com.htmlparser.parser.tagclass.TagUnorderedList;
import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.parser.style.StyleMargin;
import com.htmlparser.parser.style.StyleTextAlign;

public class ParagraphStyleCreator extends StyleClassCreator<ParagraphStyle> {

    @Override
    public BaseTagClass create(HtmlElement e, String className) {
        switch (e) {
            case P:
                return new TagParagraph(className);
            case UL:
                return new TagUnorderedList(className);
            case LI:
                return new TagListItem(className);
            case DIV:
                return new TagDiv(className);
        }
        return null;
    }

    @Override
    public BaseStyle[] create(ParagraphStyle style) {
        BaseStyle[] cssStyle = null;
        if (style instanceof AlignmentSpan) {
            cssStyle = new BaseStyle[1];
            cssStyle[0] = new StyleTextAlign();
        } else if (style instanceof LeadingMarginSpan) {
            cssStyle = new BaseStyle[1];
            cssStyle[0] = new StyleMargin();
        }
        if (cssStyle != null) {
            cssStyle[0].parseSpan(style);
            if (cssStyle[0].getValue() == null) {
                cssStyle[0] = null;
            }
        }
        return cssStyle;
    }

}

