package com.htmlparser.builder.builder;


import com.htmlparser.HtmlElement;

import java.util.HashMap;

public class IndexGenerator {
    private static HashMap<HtmlElement, Integer> mMap = new HashMap<HtmlElement, Integer>();

    private static void init() {
        HtmlElement[] elements = HtmlElement.values();
        for (HtmlElement element : elements) {
            mMap.put(element, 0);
        }
    }

    public static int generate(HtmlElement element) {
        if (mMap.isEmpty()) {
            init();
        }
        Integer integer = mMap.get(element);
        integer++;
        mMap.put(element, integer);
        return integer;
    }

    public static void reset() {
        mMap.clear();
    }


}
