package kz.zateyev.pdformation.servlet;

import kz.zateyev.pdformation.entity.Replacer;
import kz.zateyev.pdformation.entity.Tag;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FillerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        String filepath = (String)session.getAttribute("filepath");
        Set<Tag> tags = (Set<Tag>)session.getAttribute("tags");
        XWPFDocument document = new XWPFDocument(new FileInputStream(filepath));
        for (Tag tag : tags) {
            map.put(tag.getName(), request.getParameter(tag.getName()));
        }
        Replacer replacer = new Replacer(map);
        replacer.insertReplacers(document, 0);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/formed-document.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
