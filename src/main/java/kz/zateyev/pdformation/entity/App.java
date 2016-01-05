package kz.zateyev.pdformation.entity;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        /*HelperWord helper = new HelperWord();
        helper.createWord();*/
        String filepath = "D:\\tmp2\\tmpl.docx";
        String outpath = "D:\\tmp2\\output.docx";
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(new FileInputStream(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert doc != null;
        for (XWPFParagraph p : doc.getParagraphs()) {
            replace(p);
        }
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        replace(p);
                    }
                }
            }
        }
        try {
            doc.write(new FileOutputStream(outpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void replace(XWPFParagraph p) {
        int numberOfRuns = p.getRuns().size();
        // Collate text of all runs
        StringBuilder sb = new StringBuilder();
        for (XWPFRun r : p.getRuns()) {
            int pos = r.getTextPosition();
            if (r.getText(pos) != null) {
                sb.append(r.getText(pos));
            }
        }
        // Continue if there is text and contains "test"
        if (sb.length() > 0) {
            // Remove all existing runs
            for (int i = numberOfRuns; i >= 0; i--) {
                p.removeRun(i);
            }
            String text = sb.toString().replace("{str1}", "Zhuldyz").replace("{str2}", "Атеев");
            // Add new run with updated text
            XWPFRun run = p.createRun();
            run.setText(text);
            p.addRun(run);
        }
    }
}