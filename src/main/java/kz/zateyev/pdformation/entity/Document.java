package kz.zateyev.pdformation.entity;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class Document {
    private XWPFDocument xwpfDocument;
    private String name;

    public Document(XWPFDocument xwpfDocument, String name) {
        this.xwpfDocument = xwpfDocument;
        this.name = name;
    }

    public XWPFDocument getXwpfDocument() {
        return xwpfDocument;
    }

    public void setXwpfDocument(XWPFDocument xwpfDocument) {
        this.xwpfDocument = xwpfDocument;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}