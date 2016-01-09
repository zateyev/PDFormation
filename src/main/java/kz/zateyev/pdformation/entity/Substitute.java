package kz.zateyev.pdformation.entity;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class Substitute {
    private XWPFDocument document;
    private String toReplace;
    private String replacement;
    private String outpath = "D:\\tmp2\\output.docx";

    public Substitute(XWPFDocument document, String toReplace, String replacement) {
        this.document = document;
        this.toReplace = toReplace;
        this.replacement = replacement;
    }

    public void createDoc() {
        for (XWPFParagraph p : document.getParagraphs()) {
            replace(p);
        }
        for (XWPFTable tbl : document.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        replace(p);
                    }
                }
            }
        }
        try {
            document.write(new FileOutputStream(outpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void replace(XWPFParagraph p) {
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
            String text = sb.toString().replace(toReplace, replacement);
            // Add new run with updated text
            XWPFRun run = p.createRun();
            run.setText(text);
            p.addRun(run);
        }
    }
}
