package kz.zateyev.pdformation.entity;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        String filepath = "D:\\tmp2\\tmpl.docx";
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(new FileInputStream(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("{RegularExpression}", "Регулярное выражение");
        map.put("{Word}", "Ворд");
        map.put("{str1}", "стр1");
        map.put("{word}", "ворд");
        map.put("{anyword}", "эни ворд");
        map.put("{str2}", "стр2");
        Substitute substitute = new Substitute(doc, map);
        substitute.createDoc();

        /*Marker marker = new Marker(doc);
        System.out.println(marker.getMarkers());*/
    }
}