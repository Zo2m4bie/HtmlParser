package com.htmlparser.parser.paragraph;


import com.htmlparser.HtmlElement;

public class ParagraphParser extends AbstractParagraphParser {
    @Override
    public boolean canHandle(HtmlElement e) {
        return HtmlElement.P.equals(e);
    }

}
