package com.htmlparser.parser;

import android.text.SpannableStringBuilder;
import android.text.Spanned;


import com.htmlparser.Constants;
import com.htmlparser.HtmlElement;
import com.htmlparser.parser.character.AbstractCharacterParser;
import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.parser.style.StyleBackgroundColor;
import com.htmlparser.parser.style.StyleMargin;
import com.htmlparser.parser.style.ParagraphStyle;
import com.htmlparser.span.type.NoMergeSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractParser {

    private List<int[]> mSimpleTextRanges;

    private SpannableStringBuilder mSpannableStringBuilder;
    private AbstractParser mParentBuilder;
    private List<BaseStyle> mCssStyles;
    private HashMap<String, String> mAttributes;

    public AbstractParser() {
        mSpannableStringBuilder = new SpannableStringBuilder();
    }

    public abstract Spanned parse();

    public abstract boolean canHandle(HtmlElement e);

    public void extractStylesFromAtts(HashMap<String, String> attributes, ParserOptions parserOptions) {
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if ("style".equals(entry.getKey())) {
                String value = entry.getValue();
                List<BaseStyle> stylesList = BuilderHelper.parseStyleString(value, parserOptions);
                forceAddStyles(stylesList);
            }
        }
    }

    public SpannableStringBuilder getSpannableStringBuilder() {
        return mSpannableStringBuilder;
    }

    public List<int[]> getSimpleTextRanges() {
        return mSimpleTextRanges;
    }

    public List<BaseStyle> getCssStyles() {
        return mCssStyles;
    }

    public void appendText(String text) {
        if (mSimpleTextRanges == null) {
            mSimpleTextRanges = new ArrayList<int[]>();
        }
        int[] range = new int[2];
        range[0] = mSpannableStringBuilder.length();

        String modifiedText = text == null ? "" : text;
        mSpannableStringBuilder.append(modifiedText);
        range[1] = mSpannableStringBuilder.length();
        if (!modifiedText.equals("\n")) {
            mSimpleTextRanges.add(range);
        }
    }

    public void addStyles(List<BaseStyle> styles) {
        for (BaseStyle style : styles) {
            addStyle(style);
        }
    }

    public void forceAddStyles(List<BaseStyle> styles) {
        for (BaseStyle style : styles) {
            forceAdd(style);
        }
    }

    public void addStyle(BaseStyle style) {
        if (mCssStyles == null) {
            mCssStyles = new LinkedList<BaseStyle>();
        }
        if (style != null) {
            if (isAlreadyInList(style) == -1) {
                mCssStyles.add(style);
            }
        }
    }

    /**
     * Replace previous style
     *
     * @param style
     */
    public void forceAdd(BaseStyle style) {
        if (mCssStyles == null) {
            mCssStyles = new LinkedList<BaseStyle>();
        }
        if (style != null) {
            int index = isAlreadyInList(style);
            if (index >= 0) {
                mCssStyles.remove(index);
            }
            style.setPriority(BaseStyle.PRIORITY_HIGH);
            mCssStyles.add(style);
        }
    }

    private int isAlreadyInList(BaseStyle style) {
        int index = -1;
        for (int i = 0; i < mCssStyles.size(); i++) {
            BaseStyle s = mCssStyles.get(i);
            if (style.getClass().equals(s.getClass())) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        mAttributes = attributes;
    }

    public void setParentBuilder(AbstractParser builder) {
        mParentBuilder = builder;
    }

    public AbstractParser getParentParser() {
        return mParentBuilder;
    }

    public void finish() {
        if (mParentBuilder != null) {
            mParentBuilder.onChildBuilt(mSpannableStringBuilder);
            mParentBuilder = null;
        }
    }

    public void onChildBuilt(SpannableStringBuilder stringBuilder) {
        mSpannableStringBuilder.append(stringBuilder);
    }

    public boolean paragraphEndSymbolExist() {
        //If there is no any symbol yet - return
        if (mSpannableStringBuilder.length() == 0) {
            return false;
        }
        char first = mSpannableStringBuilder.toString().charAt(mSpannableStringBuilder.length() - 1);
        return first == '\n';
    }

    /**
     *
     * @param count How many symbols you want to add
     * @param text StringBuilder to attach symbols to
     */
    public void addInvisibleSymbol(int count, SpannableStringBuilder text) {
        if (count < 1) {
            return;
        }
        for (int i = 0; i < count; i++) {
            text.insert(0, String.valueOf(Constants.INVISIBLE_SYMBOL));
        }
        //we added invisible symbol, so we must increase range at line beginning by 1
        if (mSimpleTextRanges == null) {
            mSimpleTextRanges = new ArrayList<int[]>();
        }
        if (mSimpleTextRanges.size() == 0) {
            mSimpleTextRanges.add(new int[]{0, 0});
        }
        int range[] = mSimpleTextRanges.get(0);
        range[1] += count;
    }


    /**
     * @param key attribute name
     * @return index in array or -1 if there is no such element
     */
    protected String getAttributeValue(String key) {
        return mAttributes != null ? mAttributes.get(key) : null;
    }

    public void mergeStyles(List<BaseStyle> styles) {
        boolean ignoreBackground = false;
        if (styles == null) {
            return;
        }
        if (getParentParser().getClass().equals(BodyParser.class)) {
            ignoreBackground = true;
        }
        if (mCssStyles == null) {
            mCssStyles = new LinkedList<BaseStyle>();
        }
        for (BaseStyle externalStyle : styles) {
            if (externalStyle == null) {
                continue;
            }
            //we are not merging note bg color or image
            if (ignoreBackground && externalStyle instanceof StyleBackgroundColor) {
                continue;
            }
            //we are not merging paragraph styles to CharacterParser
            if (this instanceof AbstractCharacterParser && externalStyle instanceof ParagraphStyle) {
                continue;
            }
            //we are not merging that spans at all
            if (externalStyle instanceof NoMergeSpan) {
                continue;
            }
            boolean merge = true;
            for (BaseStyle internalStyle : mCssStyles) {
                if (internalStyle == null) {
                    continue;
                }
                final Class<? extends BaseStyle> exClass = externalStyle.getClass();
                final Class<? extends BaseStyle> inClass = internalStyle.getClass();
                if (exClass.equals(inClass)) {
                    if (externalStyle instanceof StyleMargin && internalStyle instanceof StyleMargin) {
                        int exMargin = ((StyleMargin) externalStyle).getMargin();
                        int inMargin = ((StyleMargin) internalStyle).getMargin();
                        ((StyleMargin) internalStyle).setValue(exMargin + inMargin);
                    }
                    merge = false;
                }
            }
            if (merge) {
                //for case when we are merging 'body' to childs.
                externalStyle.setPriority(BaseStyle.PRIORITY_DEFAULT);
                mCssStyles.add(externalStyle);
            }
        }
    }
}

