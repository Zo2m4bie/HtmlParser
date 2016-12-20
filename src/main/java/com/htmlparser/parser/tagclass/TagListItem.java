package com.htmlparser.parser.tagclass;

import android.text.style.CharacterStyle;
import android.text.style.ParagraphStyle;


import com.htmlparser.HtmlElement;
import com.htmlparser.parser.style.BaseStyle;

import java.util.ArrayList;
import java.util.List;

public class TagListItem extends BaseTagClass {
    public TagListItem(String className) {
        super(HtmlElement.LI, className);
    }

    @Override
    public List<CharacterStyle> getAndroidCharacterSpan() {
        List<CharacterStyle> characterStyleArrayList = new ArrayList<CharacterStyle>();
        List<BaseStyle> cssStyles = getStylesList();
        for(BaseStyle cssStyle : cssStyles){
            characterStyleArrayList.add(cssStyle.getAndroidCharacterSpan());
        }

        return characterStyleArrayList;
    }

    @Override
    public List<ParagraphStyle> getAndroidParagraphSpan() {
        return null;
    }

    @Override
    public CssClassType getClassType() {
        return CssClassType.PARAGRAPH;
    }
}
