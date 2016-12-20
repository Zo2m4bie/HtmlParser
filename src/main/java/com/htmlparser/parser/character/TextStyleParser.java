package com.htmlparser.parser.character;


import com.htmlparser.HtmlElement;
import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.parser.style.StyleFontStyle;
import com.htmlparser.parser.style.StyleFontWeight;
import com.htmlparser.parser.style.StyleTextDecoration;

public class TextStyleParser extends AbstractCharacterParser {


    public TextStyleParser(HtmlElement e) {
        BaseStyle style = new StyleTextDecoration();
        switch (e) {
            case B:
                style = new StyleFontWeight();
                ((StyleFontWeight) style).setValue(StyleFontWeight.CssFontWeight.BOLD);
                break;
            case I:
                style = new StyleFontStyle();
                ((StyleFontStyle) style).setValue(StyleFontStyle.CssFontStyle.ITALIC);
                break;
            case U:
                ((StyleTextDecoration) style).setValue(StyleTextDecoration.CssTextDecoration.UNDERLINE);
                break;
        }
        addStyle(style);
    }

    @Override
    public boolean canHandle(HtmlElement e) {
        return HtmlElement.B.equals(e) || HtmlElement.U.equals(e) || HtmlElement.I.equals(e);
    }


}
