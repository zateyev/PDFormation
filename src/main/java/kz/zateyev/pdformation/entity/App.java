package kz.zateyev.pdformation.entity;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        String filepath = "D:\\tmp2\\tmpl.docx";
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(new FileInputStream(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*Substitute substitute = new Substitute(doc, "{str1}", "Zhasulan Атеев");
        substitute.createDoc();*/

        Marker marker = new Marker(doc);
        System.out.println(marker.getMarkers());
    }
}