package com.htmlparser.parser.paragraph;

import com.htmlparser.HtmlElement;

public class BlockquoteParser extends AbstractParagraphParser {
    @Override
    public boolean canHandle(HtmlElement e) {
        return HtmlElement.BLOCKQUOTE.equals(e);
    }
}
