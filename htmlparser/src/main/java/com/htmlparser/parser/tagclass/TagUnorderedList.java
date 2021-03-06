package com.htmlparser.parser.tagclass;

import android.text.style.CharacterStyle;
import android.text.style.ParagraphStyle;


import com.htmlparser.HtmlElement;
import com.htmlparser.parser.style.BaseStyle;

import java.util.ArrayList;
import java.util.List;

public class TagUnorderedList extends BaseTagClass {
    public TagUnorderedList(String className) {
        super(HtmlElement.UL, className);
    }

    @Override
    public List<CharacterStyle> getAndroidCharacterSpan() {
        return null;
    }

    @Override
    public List<ParagraphStyle> getAndroidParagraphSpan() {
        List<ParagraphStyle> paragraphSpans = new ArrayList<ParagraphStyle>();
        List<BaseStyle> cssStyles = getStylesList();
        for (BaseStyle cssStyle : cssStyles) {
            paragraphSpans.add(cssStyle.getAndroidParagraphSpan());
        }

        return paragraphSpans;
    }

    @Override
    public CssClassType getClassType() {
        return CssClassType.LIST;
    }
}
