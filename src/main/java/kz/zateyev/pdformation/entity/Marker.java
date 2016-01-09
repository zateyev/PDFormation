package kz.zateyev.pdformation.entity;

import org.apache.poi.xwpf.usermodel.*;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Marker {
    XWPFDocument document;

    public Marker(XWPFDocument document) {
        this.document = document;
    }

    public Set<String> getMarkers() {
        Set<String> marker = new HashSet<String>();
        String regex = "(\\{\\w*?\\})";
        String text = null;
        for (XWPFParagraph p : document.getParagraphs()) {
            text = getText(p);
        }
        for (XWPFTable tbl : document.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        text = getText(p);
                    }
                }
            }
        }
        Pattern pattern = Pattern.compile(regex);
        assert text != null;
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            marker.add(matcher.group());
        }
        return marker;
    }

    private String getText(XWPFParagraph p) {
        StringBuilder sb = new StringBuilder();
        for (XWPFRun r : p.getRuns()) {
            int pos = r.getTextPosition();
            if (r.getText(pos) != null) {
                sb.append(r.getText(pos));
            }
        }
        return sb.toString();
    }
}
