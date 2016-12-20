package com.htmlparser.builder.section;

import android.text.style.CharacterStyle;

import com.htmlparser.HtmlElement;
import com.htmlparser.builder.builder.IndexGenerator;
import com.htmlparser.parser.creator.CharacterStyleCreator;
import com.htmlparser.parser.creator.Creators;
import com.htmlparser.parser.tagclass.BaseTagClass;
import com.htmlparser.parser.tagclass.TagSpan;
import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.span.CustomUrlSpan;

public class SectionFactory {
    public static Section createSection(CharacterStyle[] styles, int sectionStart, int sectionEnd, Creators creator) {
        Section section = null;
        CustomUrlSpan customUrlSpan = (CustomUrlSpan) getSpan(CustomUrlSpan.class, styles);

        if (customUrlSpan != null) {
            section = new LinkSection(sectionStart, sectionEnd, customUrlSpan);
        } else if (styles.length > 0) {
            BaseTagClass cssClass = generateSpanClass(styles, HtmlElement.SPAN.toString() + IndexGenerator.generate(HtmlElement.SPAN), creator);
            section = new StyleSection(sectionStart, sectionEnd, cssClass);
        } else {
            section = new TextSection(sectionStart, sectionEnd);
        }

        return section;
    }

    private static <T> CharacterStyle getSpan(Class<T> clazz, CharacterStyle[] styles) {
        CharacterStyle span = null;
        for (CharacterStyle style : styles) {
            if (style != null) {
                if (clazz.isAssignableFrom(style.getClass())) {
                    span = style;
                    break;
                }
            }
        }
        return span;
    }

    public static TagSpan generateSpanClass(CharacterStyle[] styles, String className, Creators creator) {
        final CharacterStyleCreator characterStyleCreator = creator.getCharacterStyleCreator();

        BaseTagClass cssClass = characterStyleCreator.create(HtmlElement.SPAN, className);
        for (CharacterStyle span : styles) {
            if (span != null) {
                BaseStyle[] cssStyles = characterStyleCreator.create(span);
                for (BaseStyle style : cssStyles) {
                    if (style != null && style.getValue() != null) {
                        cssClass.addStyle(style);
                    }
                }
            }
        }
        if (cssClass.getStylesList().size() > 0) {
            return (TagSpan) cssClass;
        } else {
            return null;
        }
    }

}
