package com.htmlparser.parser.tagclass;

import android.text.style.CharacterStyle;
import android.text.style.ParagraphStyle;

import com.htmlparser.HtmlElement;

import java.util.List;

public class TagBody extends BaseTagClass {
    public TagBody(HtmlElement element, String className) {
        super(element, className);
    }

    @Override
    public List<CharacterStyle> getAndroidCharacterSpan() {
        return null;
    }

    @Override
    public List<ParagraphStyle> getAndroidParagraphSpan() {
        return null;
    }



    @Override
    public CssClassType getClassType() {
        return null;
    }
}
