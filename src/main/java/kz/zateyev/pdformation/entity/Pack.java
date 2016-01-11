package kz.zateyev.pdformation.entity;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.List;

public class Pack {
    private String name;
    private List<XWPFDocument> documents;

    public Pack() {
    }

    public Pack(String name, List<XWPFDocument> documents) {
        this.name = name;
        this.documents = documents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<XWPFDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<XWPFDocument> documents) {
        this.documents = documents;
    }
}
