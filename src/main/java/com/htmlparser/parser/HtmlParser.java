package com.htmlparser.parser;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ParagraphStyle;
import android.util.Log;

import com.htmlparser.Constants;

import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;

public class HtmlParser {

    public static final String TAG = HtmlParser.class.getName();

    private XMLReader mReader;
    private ParserOptions mOptions;

    public HtmlParser() {
        init();
    }

    public HtmlParser(ParserOptions options) {
        init();
        this.mOptions = options;
    }

    private void init() {
        SAXParserImpl parser = null;
        try {
            parser = SAXParserImpl.newInstance(null);
        } catch (SAXException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        if (parser != null) {
            mReader = parser.getXMLReader();
        }
    }

    public Spanned parse(String text) {
        HTMLContentHandler contentHandler = new HTMLContentHandler(mOptions);
        mReader.setContentHandler(contentHandler);
        try {
            mReader.parse(new InputSource(new StringReader(text)));
        } catch (IOException e) {
            // We are reading from a string. There should not be IO problems.
            throw new RuntimeException(e);
        } catch (SAXException e) {
            // TagSoup doesn't throw parse exceptions.
            throw new RuntimeException(e);
        }
        return changeSpannableAfterOpen(contentHandler.getResult());
    }

    private Spanned changeSpannableAfterOpen(Spanned result) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(result);
        for (int i = 0; i < ssb.length(); ) {
            if (ssb.charAt(i) == Constants.INVISIBLE_SYMBOL) {
                ssb.replace(i, i + 1, "");
            } else {
                i++;
            }
        }

        return result;
    }

}
