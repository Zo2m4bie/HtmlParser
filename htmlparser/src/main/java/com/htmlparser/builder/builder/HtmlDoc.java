package com.htmlparser.builder.builder;


import com.htmlparser.parser.tagclass.BaseTagClass;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class HtmlDoc {

    private Element mHtml;
    private Element mHead;
    private Element mBody;
    private Document mDoc;

    public HtmlDoc() {
        createBsicHtmlDocument();
    }

    private void createBsicHtmlDocument() {
        mDoc = null;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            mDoc = builder.newDocument();
            mDoc.setXmlVersion("1.0");

            mHtml = mDoc.createElement("html");
            mHead = mDoc.createElement("head");
            mHtml.appendChild(mHead);
            mBody = mDoc.createElement("body");
            mHtml.appendChild(mBody);
            mDoc.appendChild(mHtml);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Element createElement(String name, BaseTagClass cssClass, String text) {
        Element element = mDoc.createElement(name);
        if (cssClass != null) {
            element.setAttribute("class", cssClass.getClassName());
        }
        if (text != null) {
            element.appendChild(mDoc.createTextNode(text));
        }

        return element;
    }

    public Text createTextElement(String text){
        return mDoc.createTextNode(text);
    }

    public Element getBody() {
        return mBody;
    }

    public Document getDoc() {
        return mDoc;
    }

}

