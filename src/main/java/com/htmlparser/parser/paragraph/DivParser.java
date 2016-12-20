package com.htmlparser.parser.paragraph;


import com.htmlparser.HtmlElement;

public class DivParser extends AbstractParagraphParser {
    @Override
    public boolean canHandle(HtmlElement e) {
        return HtmlElement.DIV.equals(e);
    }
}
