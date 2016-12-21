package com.htmlparser;

public enum HtmlElement {
    P("p"), SPAN("span"), LI("li"), OL("ol"),
    UL("ul"), ULC("ul"), A("a"), BODY("body"), DIV("div"),
    FONT("font"), BLOCKQUOTE("blockquote"), B("b"), I("i"), U("u"),
    BR("br");


    private String mName;

    HtmlElement(String name) {
        mName = name;
    }


    @Override
    public String toString() {
        return mName;
    }

    public static HtmlElement toElement(String name) {
        if ("p".equals(name)) {
            return P;
        } else if ("span".equals(name)) {
            return SPAN;
        } else if ("ul".equals(name)) {
            return UL;
        } else if ("ol".equals(name)) {
            return OL;
        } else if ("li".equals(name)) {
            return LI;
        } else if ("body".equals(name)) {
            return BODY;
        } else if ("a".equals(name)) {
            return A;
        } else if ("ulc".equals(name)) {
            return UL;
        } else if ("div".equals(name)) {
            return DIV;
        } else if ("font".equals(name)) {
            return FONT;
        } else if ("blockquote".equals(name)) {
            return BLOCKQUOTE;
        } else if ("u".equals(name)) {
            return U;
        } else if ("i".equals(name)) {
            return I;
        } else if ("b".equals(name)) {
            return B;
        } else if("br".equals(name)){
            return BR;
        }
        return null;
    }
}
