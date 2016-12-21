package com.htmlparser.parser;

import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public abstract class TypefaceUtil {

    public static final String FONT_DEFAULT = "Default";
    //Fonts for next release
    public static final String FONT_ARIAL = "Arial";
    public static final String FONT_AVENIR_NEXT = "Avenir Next";
    public static final String FONT_AVENIR_NEXT_CONDENSED = "Avenir Next Condensed";
    public static final String FONT_BASKERVILLE = "Baskerville";
    public static final String FONT_COCHIN = "Cochin";
    private static final String FONT_COURIER = "Courier";
    private static final String FONT_COURIER_NEW = "Courier New";
    private static final String FONT_GEORGIA = "Georgia";
    private static final String FONT_GILL_SANS = "Gill Sans";
    private static final String FONT_HELVETICA = "Helvetica";
    private static final String FONT_OPTIMA = "Optima";
    private static final String FONT_PALATINO = "Palatino";
    private static final String FONT_TIMES = "Times New Roman";
    private static final String FONT_TREBUCHET = "Trebuchet";
    private static final String FONT_VERDANA = "Verdana";

    private static Map<String, Typeface> typefaceMap;

    public static Typeface getTypeFace(String fontFaceName) {
        if (typefaceMap == null) {
            initTypefaceMap();
        }
        return typefaceMap.get(fontFaceName);
    }

    private static void initTypefaceMap() {
        typefaceMap = new HashMap<String, Typeface>();
        typefaceMap.put(FONT_DEFAULT, Typeface.DEFAULT);
        typefaceMap.put(FONT_ARIAL, getDroidSans());
        typefaceMap.put(FONT_AVENIR_NEXT, getDroidSans());
        typefaceMap.put(FONT_AVENIR_NEXT_CONDENSED, getDroidSans());
        typefaceMap.put(FONT_BASKERVILLE, getDroidSerif());
        typefaceMap.put(FONT_COCHIN, getDroidSerif());
        typefaceMap.put(FONT_COURIER, getDroidSans());
        typefaceMap.put(FONT_COURIER_NEW, getDroidSans());
        typefaceMap.put(FONT_GEORGIA, getDroidSerif());
        typefaceMap.put(FONT_GILL_SANS, getDroidSans());
        typefaceMap.put(FONT_HELVETICA, getDroidSans());
        typefaceMap.put(FONT_OPTIMA, getDroidSans());
        typefaceMap.put(FONT_PALATINO, getDroidSerif());
        typefaceMap.put(FONT_TIMES, getDroidSerif());
        typefaceMap.put(FONT_TREBUCHET, getDroidSans());
        typefaceMap.put(FONT_VERDANA, getDroidSans());
    }

    private static Typeface getDroidSans() {
        return Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
    }

    private static Typeface getDroidSerif() {
        return Typeface.create(Typeface.SERIF, Typeface.NORMAL);
    }
}
