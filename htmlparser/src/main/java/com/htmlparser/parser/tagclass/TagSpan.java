package com.htmlparser.parser.tagclass;

import android.text.style.CharacterStyle;
import android.text.style.ParagraphStyle;

import com.htmlparser.HtmlElement;
import com.htmlparser.parser.style.BaseStyle;

import java.util.ArrayList;
import java.util.List;

public class TagSpan extends BaseTagClass {

    public TagSpan(String className) {
        super(HtmlElement.SPAN, className);
    }

    @Override
    public List<CharacterStyle> getAndroidCharacterSpan() {
        List<CharacterStyle> spans = new ArrayList<CharacterStyle>();
        List<BaseStyle> styleList = getStylesList();
        for(BaseStyle cssStyle : styleList){
            spans.add(cssStyle.getAndroidCharacterSpan());
        }
        return spans;
    }

    @Override
    public List<ParagraphStyle> getAndroidParagraphSpan() {
        return null;
    }

    @Override
    public CssClassType getClassType() {
        return CssClassType.SPAN;
    }
}
