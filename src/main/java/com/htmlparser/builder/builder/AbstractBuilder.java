package com.htmlparser.builder.builder;


import com.htmlparser.HtmlElement;
import com.htmlparser.parser.tagclass.BaseTagClass;
import com.htmlparser.parser.style.BaseStyle;

import org.w3c.dom.Element;

import java.util.List;

public abstract class AbstractBuilder {

    protected static final String KEY_CSS_CLASS_NAME = "class";
    protected HtmlElement ELEMENT;
    private Element mElement;
    private HtmlDoc mDocument;
    private List<BaseTagClass> mCssClasses;

    public AbstractBuilder(HtmlDoc htmlDoc, List<BaseTagClass>  cssClassList) {
        this.mDocument = htmlDoc;
        this.mCssClasses = cssClassList;
        setElement();
    }

    public void initElement(Element rootElement) {
        mElement = createElement(getElementName());
        BaseTagClass paragraphCssClass = getCssClass();
        if (paragraphCssClass != null) {
            StringBuilder sb = new StringBuilder();
            for(BaseStyle style : paragraphCssClass.getStylesList()){
                sb.append(style.toString());
            }
            mElement.setAttribute("style", sb.toString());
        } else {
            mElement.setAttribute("style", "");
        }
        rootElement.appendChild(mElement);
    }

    public abstract BaseTagClass getCssClass();

    public abstract boolean canProcess(List<BaseTagClass> cssClasses);

    public abstract void process(List<BaseTagClass> cssClasses);

    public abstract String getElementName();

    public void setCurrentElement(Element element) {
        this.mElement = element;
    }

    public Element getCurrentElement() {
        return mElement;
    }

    public List<BaseTagClass> getCssClasses() {
        return mCssClasses;
    }

    protected abstract void setElement();

    protected Element createElement(String name) {
        return mDocument.createElement(name, null, null);
    }
}
