package kz.zateyev.pdformation.action;

import kz.zateyev.pdformation.entity.Pack;
import kz.zateyev.pdformation.entity.Replacer;
import kz.zateyev.pdformation.entity.Tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FillerAction implements Action {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        Set<Tag> tags = (Set<Tag>)session.getAttribute("tags");
        for (Tag tag : tags) {
            map.put("{" + tag.getName() + "}", request.getParameter(tag.getName()));
        }
        Pack pack = (Pack) session.getAttribute("pack");
        Replacer replacer = new Replacer(map);
        replacer.insertReplacers(pack);
        return new View("formed-document", true);
    }
}