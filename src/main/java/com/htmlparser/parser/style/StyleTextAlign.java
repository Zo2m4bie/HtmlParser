package com.htmlparser.parser.style;

import android.text.Layout;
import android.text.style.CharacterStyle;

import com.htmlparser.span.AlignmentSpan;
import com.htmlparser.span.TextAlignment;


public class StyleTextAlign extends BaseStyle implements ParagraphStyle {


    @Override
    public void setKey() {
        mKey = "text-align";
    }

    @Override
    public void parseSpan(CharacterStyle style) {
    }

    @Override
    public void parseSpan(android.text.style.ParagraphStyle style) {
        TextAlignment align = TextAlignment.LEFT;
        if (style instanceof AlignmentSpan) {
            align = ((AlignmentSpan) style).getTextAlignment();
        } else {
            Layout.Alignment alignment = ((android.text.style.AlignmentSpan)style).getAlignment();
            align = TextAlignment.getFromLayoutAlignment(alignment);
        }
        setValue(align.toString());
    }

    @Override
    public android.text.style.ParagraphStyle getAndroidParagraphSpan() {
        AlignmentSpan alignmentSpan = null;
        if (TextAlignment.CENTER.toString().equals(mValue)) {
            alignmentSpan = new AlignmentSpan(TextAlignment.CENTER);
        } else if (TextAlignment.RIGHT.toString().equals(mValue)) {
            alignmentSpan = new AlignmentSpan(TextAlignment.RIGHT);
        } else if (TextAlignment.LEFT.toString().equals(mValue)) {
            alignmentSpan = new AlignmentSpan(TextAlignment.LEFT);
        } else if (TextAlignment.JUSTIFY.toString().equals(mValue)) {
            alignmentSpan = new AlignmentSpan(TextAlignment.JUSTIFY);
        }
        return alignmentSpan;
    }

    @Override
    public CharacterStyle getAndroidCharacterSpan() {
        return null;
    }

}
