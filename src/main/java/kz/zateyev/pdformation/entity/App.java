package kz.zateyev.pdformation.entity;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {
        String filepath = "D:\\tmp2\\tmpl.docx";
        String filepath2 = "D:\\tmp2\\tmpl2.docx";
        XWPFDocument doc = null;
        XWPFDocument doc2 = null;
        try {
            doc = new XWPFDocument(new FileInputStream(filepath));
            doc2 = new XWPFDocument(new FileInputStream(filepath2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<XWPFDocument> documents = new ArrayList<XWPFDocument>();
        documents.add(doc);
        documents.add(doc2);
        Pack pack = new Pack("Hosting", documents);

        /*Marker marker = new Marker();
        System.out.println(marker.getMarkers(pack));*/

        Map<String, String> map = new HashMap<String, String>();
        map.put("{RegularExpression}", "Регулярное выражение");
        map.put("{RegularExpression2}", "Регулярное выражение2");
        map.put("{Word}", "Ворд");
        map.put("{str1}", "стр1");
        map.put("{word}", "ворд");
        map.put("{anyword}", "эни ворд");
        map.put("{str2}", "стр2");
        map.put("{str3}", "стр3");
        Replacer replacer = new Replacer(map);
        replacer.insertReplacers(pack);

    }
}