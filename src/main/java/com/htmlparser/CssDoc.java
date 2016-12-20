package com.htmlparser;


import com.htmlparser.parser.ParserOptions;
import com.htmlparser.parser.creator.Creators;
import com.htmlparser.parser.cssclass.CssClass;

import java.util.ArrayList;
import java.util.List;

public class CssDoc {

    private List<CssClass> mClassesList = new ArrayList<CssClass>();

    public CssDoc() {
    }

    public boolean addClass(CssClass clazz, boolean isIgnoreExist) {
        boolean result = false;
        if (clazz != null && (isIgnoreExist || !isClassExist(clazz)) && clazz.getStylesList().size() > 0) {
            result = true;
            mClassesList.add(clazz);
        }
        return result;
    }

    public void addClasses(List<CssClass> list) {
        for (CssClass cssClass : list) {
            addClass(cssClass, false);
        }
    }

    public void updateClassList(List<CssClass> list) {
        for (int i = 0; i < list.size(); i++) {
            CssClass cssClass = list.get(i);
            list.remove(i);
            cssClass = getClass(cssClass);
            list.add(i, cssClass);
        }
    }

    /**
     * If we already have that class in CssDoc, we should return it.
     *
     * @param cssClass
     * @return
     */
    public CssClass getClass(CssClass cssClass) {

        int result = -1;
        for (int i = 0; i < mClassesList.size(); i++) {
            CssClass clazz = mClassesList.get(i);
            if (clazz.equals(cssClass)) {
                result = i;
                break;
            }
        }
        if (result >= 0) {
            return mClassesList.get(result);
        } else {
            return null;
        }
    }

    public CssClass getClass(String className) {
        CssClass result = null;
        for (CssClass clazz : mClassesList) {
            String docClassName = clazz.getClassName().trim();
            if (docClassName.equals(className)) {
                result = clazz;
            }
        }
        return result;
    }

    /**
     * @param cssClass
     * @return class position in mClassesLsit array, on -1 if there is no class
     */
    public boolean isClassExist(CssClass cssClass) {
        boolean result = false;
        for (int i = 0; i < mClassesList.size(); i++) {
            CssClass clazz = mClassesList.get(i);
            if (clazz.equals(cssClass)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (CssClass clazz : mClassesList) {
            out.append(clazz.toString());
        }
        return out.toString();
    }


    public static CssDoc createFromText(String text, ParserOptions options) {
        CssDoc doc = new CssDoc();
        String[] classes = getStringsForClasses(text);
        Creators styleCreator = new Creators(options);
        for (String clazz : classes) {
            CssClass cssClass = CssClass.parseFromString(clazz, styleCreator);
            doc.addClass(cssClass, true);
        }
        return doc;
    }

    private static String[] getStringsForClasses(String text) {
        String[] classes;
        classes = text.split("\\}");

        for (int i = 0; i < classes.length; i++) {
            if (classes[i].equals("\n")) {
                classes[i] = "{" + classes[i] + "}";
            } else {
                classes[i] += "}";
            }
        }
        return classes;
    }
}
