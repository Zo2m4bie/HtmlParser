package com.htmlparser.builder.builder;

import android.text.TextUtils;


import com.htmlparser.HtmlElement;
import com.htmlparser.parser.tagclass.BaseTagClass;
import com.htmlparser.span.CustomUrlSpan;

import org.w3c.dom.Element;

import java.util.List;

public class LinkBuilder extends AbstractBuilder {

    public LinkBuilder(HtmlDoc htmlDoc) {
        super(htmlDoc, null);
    }

    public void initElement(Element rootElement, CustomUrlSpan urlSpan) {
        super.initElement(rootElement);
        Element a = getCurrentElement();
        if(urlSpan.getURL() != null){
            a.setAttribute("href", urlSpan.getURL());
        }
        if(!TextUtils.isEmpty(urlSpan.getTitle())) {
            a.setAttribute("title", urlSpan.getTitle());
        }
    }

    @Override
    public BaseTagClass getCssClass() {
        return null;
    }

    @Override
    public boolean canProcess(List<BaseTagClass> cssClasses) {
        return false;
    }

    @Override
    public void process(List<BaseTagClass> cssClasses) {
    }

    @Override
    public String getElementName() {
        return ELEMENT.toString();
    }

    @Override
    protected void setElement() {
        ELEMENT = HtmlElement.A;
    }
}
