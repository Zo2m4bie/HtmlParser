package com.htmlparser.builder.builder;

import com.htmlparser.HtmlElement;
import com.htmlparser.builder.section.StyleSection;
import com.htmlparser.builder.section.LinkSection;
import com.htmlparser.builder.section.Section;
import com.htmlparser.parser.tagclass.BaseTagClass;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class BuilderFactory {
    private HtmlDoc mHtmlDoc;

    public BuilderFactory(HtmlDoc doc) {
        mHtmlDoc = doc;
    }

    public AbstractBuilder newInstance(HtmlElement element, List<BaseTagClass> cssClasses, Element rootElement, Object... args) {
        AbstractBuilder abstractBuilder = null;

        switch (element) {
            case P:
                abstractBuilder = new ParagraphBuilder(mHtmlDoc, cssClasses);
                break;
            case SPAN:
                abstractBuilder = new SpanBuilder(mHtmlDoc, cssClasses);
                break;
            case UL:
                abstractBuilder = new UnorderedListBuilder(mHtmlDoc, cssClasses);
                break;
            case DIV:
                abstractBuilder = new DivBuilder(mHtmlDoc, cssClasses);
                break;
            case LI:
                abstractBuilder = new ListElementBuilder(mHtmlDoc, cssClasses);
                break;

        }
        if(abstractBuilder != null){
            abstractBuilder.initElement(rootElement);
        }
        return abstractBuilder;
    }


    public AbstractBuilder newInstance(Section section, Element rootElement) {
        AbstractBuilder abstractBuilder = null;
        List<BaseTagClass> list = null;
        if (section instanceof StyleSection) {
            list = new ArrayList<BaseTagClass>(1);
            final BaseTagClass cssClass = ((StyleSection) section).getCssClass();
            list.add(cssClass);
            abstractBuilder = new SpanBuilder(mHtmlDoc, list);
            abstractBuilder.initElement(rootElement);
        } else if (section instanceof LinkSection) {
            abstractBuilder = new LinkBuilder(mHtmlDoc);
            ((LinkBuilder)abstractBuilder).initElement(rootElement, ((LinkSection) section).getCustomUrlSpan());
        }
        return abstractBuilder;
    }
}
