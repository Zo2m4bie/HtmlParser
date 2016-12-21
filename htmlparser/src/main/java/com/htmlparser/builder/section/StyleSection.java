package com.htmlparser.builder.section;


import com.htmlparser.parser.tagclass.BaseTagClass;

public class StyleSection extends Section {
    private BaseTagClass mCssClass;

    public StyleSection(int start, int end, BaseTagClass cssClass) {
        super(start, end);
        this.mCssClass = cssClass;

    }

    public BaseTagClass getCssClass() {
        return mCssClass;
    }

    public void setCssClass(BaseTagClass cssClass) {
        mCssClass = cssClass;
    }

}
