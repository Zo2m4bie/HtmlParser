package com.htmlparser.span;

public class SpanContainer {

    private Object mStyle;
    private int mStart;
    private int mEnd;

    public SpanContainer(int start, int end, Object style){
        mStart = start;
        mEnd = end;
        mStyle = style;
    }

    public Object getStyle() {
        return mStyle;
    }

    public void setStyle(Object mStyle) {
        this.mStyle = mStyle;
    }

    public int getStart() {
        return mStart;
    }

    public void setStart(int mStart) {
        this.mStart = mStart;
    }

    public int getEnd() {
        return mEnd;
    }

    public void setEnd(int mEnd) {
        this.mEnd = mEnd;
    }

}
