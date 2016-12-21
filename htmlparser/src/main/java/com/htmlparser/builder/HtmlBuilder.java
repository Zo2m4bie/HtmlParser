package com.htmlparser.builder;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.htmlparser.Constants;
import com.htmlparser.StyleDoc;
import com.htmlparser.HtmlElement;
import com.htmlparser.builder.builder.AbstractBuilder;
import com.htmlparser.builder.builder.BuilderFactory;
import com.htmlparser.builder.builder.IndexGenerator;
import com.htmlparser.builder.builder.HtmlDoc;
import com.htmlparser.builder.section.StyleSection;
import com.htmlparser.builder.section.LinkSection;
import com.htmlparser.builder.section.Section;
import com.htmlparser.builder.section.TextSection;
import com.htmlparser.parser.BuilderHelper;
import com.htmlparser.parser.ParserOptions;
import com.htmlparser.parser.TextUtil;
import com.htmlparser.parser.creator.Creators;
import com.htmlparser.parser.tagclass.BaseTagClass;
import com.htmlparser.parser.tagclass.TagBody;
import com.htmlparser.parser.style.BaseStyle;
import com.htmlparser.parser.style.StyleBackgroundColor;
import com.htmlparser.span.BodySpan;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class HtmlBuilder {

    private final Creators mCreator;
    private StyleDoc mCssDoc;
    private StyleDoc mCssDocForWholeText;
    private HtmlDoc mHtmlDoc;
    private BuilderFactory mBuilderFactory;

    private Spanned mText;

    public HtmlBuilder() {
        mHtmlDoc = new HtmlDoc();
        mCssDoc = new StyleDoc();
        mCssDocForWholeText = new StyleDoc();
        mBuilderFactory = new BuilderFactory(mHtmlDoc);
        mCreator = new Creators(new ParserOptions());
    }

    private static void extractBodyFormatting(Spanned text, StyleDoc cssDoc) {
        BodySpan[] bodySpans = text.getSpans(0, text.length(), BodySpan.class);
        List<BaseStyle> lists;
        if (bodySpans.length == 0) {
            lists = new LinkedList<BaseStyle>();
            //add default bgColor for Microsoft desktop
            StyleBackgroundColor bgColor = new StyleBackgroundColor();
            bgColor.setValue(Color.WHITE);
            lists.add(bgColor);
        } else {
            lists = bodySpans[0].getStylesList();
        }

        BaseTagClass cssClass = new TagBody(HtmlElement.BODY, null);
        for (BaseStyle style : lists) {
            cssClass.addStyle(style);
        }
        cssDoc.addClass(cssClass, false);
    }

    public static void buildParagraphs(Spanned[] paragraphs, HtmlDoc doc, StyleDoc cssDoc,
                                       BuilderFactory factory, Creators creator) {
        Element bodyElement = doc.getBody();

        Element rootElement = null;
        //Declare builders
        AbstractBuilder builder = null;
        AbstractBuilder prevBuilder = null;

        for (Spanned paragraph : paragraphs) {
            List<BaseStyle> stylesList = BuilderHelper.getParagraphCssStyles(paragraph, 0, paragraph.length());
            List<BaseTagClass> cssClasses = BuilderHelper.extractClassesFromStyles(stylesList);
            BaseTagClass paragraphClass = null;
            for (BaseTagClass cssClass : cssClasses) {
                if (cssClass.getClassType().equals(BaseTagClass.CssClassType.PARAGRAPH)) {
                    paragraphClass = cssClass;
                }
            }
            cssDoc.addClasses(cssClasses);
            cssDoc.updateClassList(cssClasses);

            paragraph = removeInvisibleSymbol(paragraph);

            if (prevBuilder != null && prevBuilder.canProcess(cssClasses)) {
                prevBuilder.process(cssClasses);
                builder = prevBuilder;
            } else {
                HtmlElement element = BuilderHelper.getElementToWrapIn(cssClasses);
                builder = factory.newInstance(element, cssClasses, bodyElement);
            }
            rootElement = builder.getCurrentElement();
            buildToElement(paragraph, rootElement, creator, doc, cssDoc, factory, paragraphClass);

            prevBuilder = builder;
        }
    }

    public static void buildToElement(Spanned text, Element rootElement, Creators creator,
                                      HtmlDoc doc, StyleDoc cssDoc, BuilderFactory factory, BaseTagClass parentClass) {

        if (text == null) {
            return;
        }

        List<Section> sections = BuilderHelper.getTextSections(text, 0, text.length(), creator, parentClass);
        if (sections.size() == 0) {
            Element element = doc.createElement("br",null,null);
            rootElement.appendChild(element);
        }
        for (Section section : sections) {
            buildSection(text, section, rootElement, cssDoc, doc, factory, creator);
        }
    }

    public static void buildSection(Spanned text, Section section, Element rootElement,
                                    StyleDoc cssDoc, HtmlDoc doc, BuilderFactory factory, Creators creator) {
        Element element = null;
        text = (Spanned) text.subSequence(section.getStart(), section.getEnd());

        final Text textElement = doc.createTextElement(text.toString());
        if (section instanceof TextSection) {
            rootElement.appendChild(textElement);
            return;
        }
        if (section instanceof StyleSection) {
            BaseTagClass cssClass = ((StyleSection) section).getCssClass();
            boolean added = cssDoc.addClass(cssClass, false);
            if (!added) {
                cssClass = cssDoc.getClass(cssClass);
            }
            ((StyleSection) section).setCssClass(cssClass);
        }
        AbstractBuilder builder = factory.newInstance(section, rootElement);
        element = builder.getCurrentElement();
        element.appendChild(textElement);

        if (section instanceof LinkSection) {
            element.removeChild(textElement);
            SpannableStringBuilder ssb = new SpannableStringBuilder(text);
            ssb.removeSpan(((LinkSection) section).getCustomUrlSpan());
            buildToElement(ssb, element, creator, doc, cssDoc, factory, null);
        }
    }

    private static Spanned removeInvisibleSymbol(Spanned text) {
        Object[] spans = null;

        /* if paragraph is consist of INVISIBLE_SYMBOL only.
        We need to save and reset spans after deleting INVISIBLE_SYMBOL, because some spans can be lose */
        if (text.toString().equals(Constants.INVISIBLE_SYMBOL + "")
                || text.toString().equals(Constants.INVISIBLE_SYMBOL + "" + Constants.INVISIBLE_SYMBOL)) {
            spans = text.getSpans(0, text.length(), Object.class);
        }

        SpannableStringBuilder ssb = new SpannableStringBuilder(text);
        for (int i = 0; i < ssb.length(); ) {
            if (ssb.charAt(i) == Constants.INVISIBLE_SYMBOL) {
                ssb.replace(i, i + 1, "");
            } else {
                i++;
            }
        }

        if (spans != null) {
            for (Object span : spans) {
                ssb.setSpan(span, 0, 0, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return ssb;
    }

    public String build(Spanned text) {
        mText = text;
        buildBody();
        String html = transform();
        html = html.replaceAll("\\n", "");
        IndexGenerator.reset();
        return html;
    }

    public String transform() {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            Properties outFormat = new Properties();
            outFormat.setProperty(OutputKeys.INDENT, "yes");
            outFormat.setProperty(OutputKeys.METHOD, "html");
            outFormat.setProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            outFormat.setProperty(OutputKeys.VERSION, "1.0");
            outFormat.setProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.setOutputProperties(outFormat);
            DOMSource domSource =
                    new DOMSource(mHtmlDoc.getDoc());
            OutputStream output = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(output);

            transformer.transform(domSource, result);
            return output.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void buildBody() {
        //Get all text paragraphs
        Spanned[] paragraphs = TextUtil.getParagraphsForSave(mText);
        extractBodyFormatting(mText, mCssDocForWholeText);
        buildParagraphs(paragraphs, mHtmlDoc, mCssDoc, mBuilderFactory, mCreator);
    }
}