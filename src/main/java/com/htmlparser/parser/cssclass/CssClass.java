package com.htmlparser.parser.cssclass;

import android.text.style.CharacterStyle;
import android.text.style.ParagraphStyle;


import com.htmlparser.HtmlElement;
import com.htmlparser.parser.creator.Creators;
import com.htmlparser.parser.style.BaseStyle;

import java.util.LinkedList;
import java.util.List;

public abstract class CssClass {

    protected HtmlElement mHtmlElement;
    protected String mFullName;
    protected String mClassName;
    protected List<BaseStyle> mStyleList;

    public CssClass(HtmlElement element, String className) {
        mHtmlElement = element;
        mFullName = element.toString();
        mFullName += className == null ? "" : "." + className;
        mClassName = className == null ? mFullName : className;
        mStyleList = new LinkedList<BaseStyle>();
    }

    public static CssClass parseFromString(String string, Creators creators) {
        int classNameEnd = string.indexOf("{");
        if (classNameEnd == -1) {
            return null;
        }
        String fullClassName = string.substring(0, classNameEnd);
        fullClassName = fullClassName.trim();
        classNameEnd = fullClassName.length();
        String className = fullClassName.substring(fullClassName.indexOf(".") + 1, classNameEnd);
        CssClass clazz;
        if (fullClassName.contains("p.")) {
            clazz = creators.getParagraphStyleCreator().create(HtmlElement.P, className);
        } else if (fullClassName.contains("span.")) {
            clazz = creators.getCharacterStyleCreator().create(HtmlElement.SPAN, className);
        } else if (fullClassName.contains("ul.")) {
            clazz = creators.getParagraphStyleCreator().create(HtmlElement.UL, className);
        } else if (fullClassName.contains("ol.")) {
            clazz = creators.getParagraphStyleCreator().create(HtmlElement.OL, className);
        } else if (fullClassName.contains("li.")) {
            clazz = creators.getParagraphStyleCreator().create(HtmlElement.LI, className);
        } else if (fullClassName.contains("body")) {
            clazz = new CssClassBody(HtmlElement.BODY, null);
        } else if (fullClassName.contains("div")) {
            clazz = creators.getParagraphStyleCreator().create(HtmlElement.DIV, className);
        } else if (fullClassName.startsWith(".")) {
            clazz = creators.getParagraphStyleCreator().create(HtmlElement.DIV, className);
        } else {
            clazz = null;
        }
        if (clazz == null) {
            return null;
        }
        String[] stylePairs = getStringsForStyles(string);
        if (stylePairs != null) {
            for (String style : stylePairs) {
                style = style.trim();
                BaseStyle cssStyle = BaseStyle.parseFromString(style);
                if (cssStyle != null && creators.getParserOptions() != null) {
                    cssStyle.readOptions(creators.getParserOptions());
                }
                clazz.addStyle(cssStyle);
            }
        }
        return clazz;
    }

    private static String[] getStringsForStyles(String text) {
        String stylesLine = text.substring(text.indexOf("{") + 1, text.indexOf("}"));
        if (stylesLine.isEmpty()) {
            return null;
        }
        String[] stylesKeyValue = stylesLine.split(";(?![\\w\\W]*\\))|;(?=[\\w\\W]*\\()");
        return stylesKeyValue;
    }

    public HtmlElement getClassElement() {
        return mHtmlElement;
    }

    public void addStyle(BaseStyle style) {
        if (style != null && !isAlreadyExist(style)) {
            if (!isDuplicate(style)) {
                mStyleList.add(style);
            } else {
                //replace old with new
                BaseStyle oldOne = null;
                for (BaseStyle cssStyle : mStyleList) {
                    if (cssStyle.getClass().equals(style.getClass())) {
                        oldOne = cssStyle;
                        break;
                    }
                }
                mStyleList.remove(oldOne);
                mStyleList.add(style);
            }
        }
    }

    private boolean isDuplicate(BaseStyle style) {
        boolean isDuplicate = false;
        for (BaseStyle cssStyle : mStyleList) {
            if (cssStyle.getClass().equals(style.getClass())) {
                isDuplicate = true;
                break;
            }
        }
        return isDuplicate;
    }

    public String getClassName() {
        return mClassName;
    }

    private boolean isAlreadyExist(BaseStyle style) {
        boolean isExist = false;
        for (BaseStyle existingStyle : mStyleList) {
            if (existingStyle.equals(style)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public List<BaseStyle> getStylesList() {
        return mStyleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CssClass cssClass = (CssClass) o;


        if (mStyleList != null) {
            if (!hasSameStyles(mStyleList, cssClass.mStyleList)) {
                return false;
            }
        } else {
            if (cssClass.mStyleList != null) {
                return false;
            }
        }
        return true;
    }

    private boolean hasSameStyles(List<BaseStyle> list1, List<BaseStyle> list2) {
        if (list1.size() != list2.size()) {
            return false;
        } else {
            for (int i = 0; i < list1.size(); i++) {
                BaseStyle style1 = list1.get(i);
                boolean contains = false;
                for (int j = 0; j < list2.size(); j++) {
                    BaseStyle style2 = list2.get(j);
                    if (style1.equals(style2)) {
                        contains = true;
                    }
                }
                //
                if (!contains) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        return mStyleList != null ? mStyleList.hashCode() : 0;
    }

    private boolean hasStyles() {
        return mStyleList.size() > 0;
    }

    @Override
    public String toString() {
        if (hasStyles()) {
            StringBuilder builder = new StringBuilder();
            builder.append(mFullName)
                    .append("{");
            for (BaseStyle style : mStyleList) {
                builder.append(style.toString());
            }
            builder.append("}\n");
            return builder.toString();
        } else {
            return "";
        }
    }

    public abstract List<CharacterStyle> getAndroidCharacterSpan();

    public abstract List<ParagraphStyle> getAndroidParagraphSpan();

    public abstract CssClassType getClassType();

    public enum CssClassType {
        PARAGRAPH, LIST, SPAN;
    }
}
