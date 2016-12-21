package com.htmlparser.parser;

import android.text.Spanned;

import com.htmlparser.StyleDoc;
import com.htmlparser.HtmlElement;
import com.htmlparser.parser.character.AbstractCharacterParser;
import com.htmlparser.parser.style.BaseStyle;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Stack;

public class HTMLContentHandler extends DefaultHandler {
    private AbstractParser mParser;
    private StyleBuilder mCssBuilder;
    private ParserFactory mParserFactory;
    private Stack<AbstractParser> mParserStack;
    private ParserOptions mOptions;

    /**
     * Dynamic factory. add method setNoteColorListener()
     * Add StyleDoc to factory and let the factory select classes and pass them to builders
     * Implement setNoteColor callback only in BoydBuilder;
     */
    public HTMLContentHandler(ParserOptions options) {
        mParserStack = new Stack<AbstractParser>();
        this.mOptions = options;
    }

    public Spanned getResult() {
        if (!mParserStack.isEmpty()) {
            mParserStack.pop();
        }
        if (!mParserStack.isEmpty()) {
            mParser = mParserStack.peek();
        }
        return mParser.parse();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        HtmlElement tag = HtmlElement.toElement(localName);
        if (HtmlElement.BODY == tag && mParserFactory == null) {
            mParserFactory = new ParserFactory(new StyleDoc(), mOptions);
        }
        if ("style".equals(localName)) {
            mCssBuilder = new StyleBuilder();
        } else if (mParserFactory != null) {
            if (tag == null) {
//                Log.e("unknown element " + localName + " -on endStartElement");
                return;
            }

            AbstractParser parser = mParserFactory.newInstance(tag, atts);
            if (parser == null) {
                return;
            }

            mParser = parser;
            AbstractParser parent = null;
            if (!mParserStack.empty()) {
                parent = mParserStack.peek();
            }
            mParser.setParentBuilder(parent);
            preCompileStyles(mParser, parent);
//            addRequiredParagraphs(mParser, parent);
            mParserStack.push(mParser);
        }
    }

    private void preCompileStyles(AbstractParser parser, AbstractParser parentParser) {
        if (parentParser != null) {
            List<BaseStyle> styles = parentParser.getCssStyles();
            parser.mergeStyles(styles);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (mCssBuilder != null) {
            StyleDoc cssDoc = mCssBuilder.build(mOptions);
            mParserFactory = new ParserFactory(cssDoc, mOptions);
            mCssBuilder = null;
        }
        HtmlElement tag = HtmlElement.toElement(localName);
        if (tag == null) {
            return;
        }
        if (mParser != null && mParser.canHandle(tag)) {
            if (mParserStack.size() > 1) {
                mParser.parse();
            }
            if (!mParserStack.isEmpty()) {
                mParser = mParserStack.pop();
            }
        }
        if (!mParserStack.isEmpty()) {
            mParser = mParserStack.peek();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String bodyText = new String(ch, start, length);

        if (mCssBuilder != null) {
            mCssBuilder.appendStylesText(bodyText);
        }
        //REVIEW Why skip (length == 1 && ch[0] == '\n')
        if (mParser == null) {
            return;
        }
        if (length == 1 && ch[0] == '\n') {
            //second condition to ignore attachments
            if (mParser instanceof AbstractCharacterParser && mParser.getAttributeValue("width") == null) {
                mParser.appendText(" ");
            } else {
                return;
            }
        }

        //remove extra \n from code. All required \n would be added in ParagraphParser manually
        //If String ends with \n we change it to whitespace cause of possible desktop formatting
        //it's not 100% right decision
        if (bodyText.endsWith("\n")) {
            bodyText = bodyText.replaceAll("\n", " ");
        } else {
            bodyText = bodyText.replaceAll("\n", "");
        }
        mParser.appendText(bodyText);
    }

}
