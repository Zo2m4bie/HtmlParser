package com.htmlparser.parser.character;


import com.htmlparser.HtmlElement;

public class SpanParser extends AbstractCharacterParser {

    @Override
    public boolean canHandle(HtmlElement e) {
        return HtmlElement.SPAN.equals(e);
    }

}
