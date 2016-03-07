package kz.zateyev.pdformation.entity;

import kz.zateyev.pdformation.validator.Validator;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {

        String filepath = "D:\\tmp2\\tmpl.docx";
        String filepath2 = "D:\\tmp2\\Dogovor.docx";
        XWPFDocument doc = null;
        XWPFDocument doc2 = null;
        try {
            doc = new XWPFDocument(new FileInputStream(filepath));
            doc2 = new XWPFDocument(new FileInputStream(filepath2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Document> documents = new ArrayList<>();
        documents.add(new Document(doc, "tmpl.docx"));
        documents.add(new Document(doc2, "Dogovor.docx"));
        Pack pack = new Pack("Hosting", documents);

        Marker marker = new Marker();
        System.out.println(marker.getTags(pack));

        /*Map<String, String> map = new HashMap<String, String>();
        map.put("{RegularExpression}", "Регулярное выражение");
        map.put("{Word}", "Ворд");
        map.put("{str1}", "стр1");
        map.put("{word}", "ворд");
        map.put("{anyword}", "эни ворд");
        map.put("{str2}", "стр2");
        map.put("{city}", "Тараз");
        map.put("{address}", "ул. Кунаева 8");
        //map.put("{RegularExpression2}", "Регулярное выражение2");
        //map.put("{str3}", "стр3");
        Replacer replacer = new Replacer(map);
        replacer.insertReplacers(pack);*/
    }
}