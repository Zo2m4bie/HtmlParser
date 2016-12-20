package com.htmlparser.parser;


import com.htmlparser.StyleDoc;
import com.htmlparser.HtmlElement;
import com.htmlparser.parser.character.FontParser;
import com.htmlparser.parser.character.LinkParser;
import com.htmlparser.parser.character.SpanParser;
import com.htmlparser.parser.character.TextStyleParser;
import com.htmlparser.parser.tagclass.BaseTagClass;
import com.htmlparser.parser.paragraph.BlockquoteParser;
import com.htmlparser.parser.paragraph.DivParser;
import com.htmlparser.parser.paragraph.LineBreakParser;
import com.htmlparser.parser.paragraph.ParagraphParser;

import org.xml.sax.Attributes;

import java.util.HashMap;

public class ParserFactory {

    private StyleDoc mCssDoc;
    private ParserOptions mParserOptions;

    public ParserFactory(StyleDoc cssDoc, ParserOptions options) {
        mCssDoc = cssDoc;
        mParserOptions = options;
    }

    public AbstractParser newInstance(HtmlElement element) {
        return newInstance(element, null);
    }

    public AbstractParser newInstance(HtmlElement element, Attributes atts) {
        AbstractParser abstractParser = null;
        String cssClassName = null;
        if (atts != null) {
            String classAttr = atts.getValue("class");
            //If we don't have particular class try to look for general formatting
            // Example: look for p {} instead of p.p1 {};
            cssClassName = classAttr == null ? element.toString() : classAttr;
        }

        if (element == null) {
            return null;
        }
        switch (element) {
            case SPAN:
                abstractParser = new SpanParser();
                break;
            case P:
                abstractParser = new ParagraphParser();
                break;
            case A:
                abstractParser = new LinkParser();
                break;
            case BODY:
                abstractParser = new BodyParser();
                break;
            case DIV:
                abstractParser = new DivParser();
                break;
            case FONT:
                abstractParser = new FontParser();
                break;
            case BLOCKQUOTE:
                abstractParser = new BlockquoteParser();
                break;
            case B:
            case U:
            case I:
                abstractParser = new TextStyleParser(element);
                break;
            case BR:
                abstractParser = new LineBreakParser();
                break;
        }
        if (abstractParser == null) {
            return null;
        }
        addStylesFromCssDoc(abstractParser, cssClassName);
        addAttributes(abstractParser, atts);
        return abstractParser;
    }


    private void addStylesFromCssDoc(AbstractParser parser, String cssClassName) {

        if (cssClassName == null) {
            return;
        }
        BaseTagClass cssClass = mCssDoc.getClass(cssClassName);
        if (cssClass != null) {
            parser.addStyles(cssClass.getStylesList());
        }

    }

    private void addAttributes(AbstractParser parser, Attributes atts) {
        if (atts.getLength() > 0) {
            HashMap<String, String> attributes = new HashMap<String, String>();
            for (int i = 0; i < atts.getLength(); i++) {
                String name = atts.getLocalName(i);
                String value = atts.getValue(name);
                attributes.put(name, value);
            }
            parser.setAttributes(attributes);

            //Try to get extra formatting styles which can be under 'style' attribute
            parser.extractStylesFromAtts(attributes, mParserOptions);
        }
    }
}
