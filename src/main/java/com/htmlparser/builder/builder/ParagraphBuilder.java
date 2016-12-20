package com.htmlparser.builder.builder;

import com.htmlparser.HtmlElement;
import com.htmlparser.parser.BuilderHelper;
import com.htmlparser.parser.tagclass.BaseTagClass;

import java.util.List;

public class ParagraphBuilder extends AbstractBuilder {


    public ParagraphBuilder(HtmlDoc doc, List<BaseTagClass> cssClassList) {
        super(doc, cssClassList);
    }

    @Override
    public BaseTagClass getCssClass() {
        List<BaseTagClass> cssClasses = getCssClasses();
        return BuilderHelper.getClassOfType(BaseTagClass.CssClassType.PARAGRAPH, cssClasses);
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
        ELEMENT = HtmlElement.P;
    }
}
