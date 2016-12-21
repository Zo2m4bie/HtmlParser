package com.htmlparser.span;

import android.text.Layout;

public enum TextAlignment {
    LEFT("left"), RIGHT("right"), CENTER("center"), JUSTIFY("justify");

    private String name;

    TextAlignment(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static TextAlignment getFromLayoutAlignment(Layout.Alignment a){
        TextAlignment alignment = LEFT;
        switch (a){
            case ALIGN_CENTER:
                alignment = CENTER;
                break;
            case ALIGN_OPPOSITE:
                alignment = RIGHT;
                break;
        }
        return alignment;
    }
}
