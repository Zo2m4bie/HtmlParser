package com.htmlparser.builder.builder;


import com.htmlparser.HtmlElement;
import com.htmlparser.parser.BuilderHelper;
import com.htmlparser.parser.tagclass.BaseTagClass;

import org.w3c.dom.Element;

import java.util.List;

public class UnorderedListBuilder extends AbstractBuilder {

    public UnorderedListBuilder(HtmlDoc htmlDoc, List<BaseTagClass> cssClassList) {
        super(htmlDoc, cssClassList);
        Element li = createElement("li");
        getCurrentElement().appendChild(li);
        setCurrentElement(li);
        BaseTagClass newCssClass = BuilderHelper.getClassOfType(BaseTagClass.CssClassType.PARAGRAPH, cssClassList);
        if (newCssClass != null) {
            li.setAttribute(KEY_CSS_CLASS_NAME, newCssClass.getClassName());
        }

    }

    @Override
    public BaseTagClass getCssClass() {
        final List<BaseTagClass> cssClasses = getCssClasses();
        return BuilderHelper.getClassOfType(BaseTagClass.CssClassType.LIST, cssClasses);
    }

    @Override
    public boolean canProcess(List<BaseTagClass> cssClasses) {
        BaseTagClass newListClass = BuilderHelper.getClassOfType(BaseTagClass.CssClassType.LIST, cssClasses);
        BaseTagClass oldListClass = BuilderHelper.getClassOfType(BaseTagClass.CssClassType.LIST, getCssClasses());
        return newListClass != null && oldListClass != null
                && newListClass.getClassElement().equals(oldListClass.getClassElement())
                && newListClass.equals(oldListClass);
    }

    @Override
    public void process(List<BaseTagClass> cssClasses) {
        Element element = (Element) getCurrentElement();
        String tagName = element.getTagName();
        if (tagName.equals("li")) {
            element = (Element) element.getParentNode();
        }
        Element li = createElement("li");
        element.appendChild(li);
        setCurrentElement(li);
        BaseTagClass cssClass = BuilderHelper.getClassOfType(BaseTagClass.CssClassType.PARAGRAPH, cssClasses);
        if (cssClass != null && cssClass.getStylesList().size() > 0) {
            li.setAttribute("class", cssClass.getClassName());
        }
    }

    @Override
    public String getElementName() {
        return ELEMENT.toString();
    }

    @Override
    protected void setElement() {
        ELEMENT = HtmlElement.UL;
    }
}
