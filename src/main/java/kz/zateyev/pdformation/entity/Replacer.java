package kz.zateyev.pdformation.entity;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class Replacer {
    private Map<String, String> map;

    public Replacer(Map<String, String> map) {
        this.map = map;
    }

    public void insertReplacers(Document document) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
        String outpath = resourceBundle.getString("formed.document.location");
        for (XWPFParagraph p : document.getXwpfDocument().getParagraphs()) {
            replace(p, map);
        }
        for (XWPFTable tbl : document.getXwpfDocument().getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        replace(p, map);
                    }
                }
            }
        }
        try {
            document.getXwpfDocument().write(new FileOutputStream(outpath + document.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void replace(XWPFParagraph p, Map<String, String> map) {
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
            String text = sb.toString();
            Set<String> markers = map.keySet();
            for (String marker : markers) {
                text = text.replace(marker, map.get(marker));
            }
            // Add new run with updated text
            XWPFRun run = p.createRun();
            run.setText(text);
            p.addRun(run);
        }
    }

    public void insertReplacers(Pack pack) {
        for (Document document : pack.getDocuments()) {
            insertReplacers(document);
        }
    }
}
