package com.htmlparser.parser.creator;

import com.htmlparser.parser.ParserOptions;

public class Creators {

    private CharacterStyleCreator mCharacterStyleCreator;
    private ParagraphStyleCreator mParagraphStyleCreator;
    private ParserOptions mParserOptions;

    public Creators(ParserOptions options) {
        mParserOptions = options;
        mParagraphStyleCreator = new ParagraphStyleCreator();
        mCharacterStyleCreator = new CharacterStyleCreator(options);
    }

    public CharacterStyleCreator getCharacterStyleCreator() {
        return mCharacterStyleCreator;
    }

    public ParagraphStyleCreator getParagraphStyleCreator() {
        return mParagraphStyleCreator;
    }

    public ParserOptions getParserOptions() {
        return mParserOptions;
    }
}
