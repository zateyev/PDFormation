package kz.zateyev.pdformation.entity;

import org.apache.poi.xwpf.usermodel.*;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Marker {

    public Set<String> getMarkers(XWPFDocument document) {
        Set<String> marker = new HashSet<String>();
        String regex = "(\\{\\w*?\\})";
        StringBuilder sb = new StringBuilder();
        for (XWPFParagraph p : document.getParagraphs()) {
            sb.append(getText(p));
        }
        for (XWPFTable tbl : document.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        sb.append(getText(p));
                    }
                }
            }
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sb.toString());
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

    public Set<String> getMarkers(Pack pack) {
        Set<String> markers = new HashSet<String>();
        for (XWPFDocument document : pack.getDocuments()) {
            markers.addAll(getMarkers(document));
        }
        return markers;
    }
}
