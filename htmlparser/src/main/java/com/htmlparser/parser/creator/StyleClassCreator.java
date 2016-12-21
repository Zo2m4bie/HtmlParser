package com.htmlparser.parser.creator;


import com.htmlparser.HtmlElement;
import com.htmlparser.parser.tagclass.BaseTagClass;
import com.htmlparser.parser.style.BaseStyle;

public abstract class StyleClassCreator<T> {
    public abstract BaseTagClass create(HtmlElement e, String className);
    public abstract BaseStyle[] create(T style);



}
