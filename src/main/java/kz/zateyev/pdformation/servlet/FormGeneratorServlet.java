package kz.zateyev.pdformation.servlet;

import kz.zateyev.pdformation.dao.DaoFactory;
import kz.zateyev.pdformation.dao.DocumentDao;
import kz.zateyev.pdformation.dao.PackDao;
import kz.zateyev.pdformation.entity.Document;
import kz.zateyev.pdformation.entity.Marker;
import kz.zateyev.pdformation.entity.Pack;
import kz.zateyev.pdformation.entity.Tag;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class FormGeneratorServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Long packID = Long.valueOf(request.getParameter("packid"));
        DaoFactory jdbcDaoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC);
        PackDao jdbcPackDao = jdbcDaoFactory.getPackDao();
        DocumentDao jdbcDocumentDao = jdbcDaoFactory.getDocumentDao();
        Pack pack = jdbcPackDao.findById(packID);
        List<Document> documents = jdbcDocumentDao.findByPack(pack);
        pack.setDocuments(documents);
        Marker marker = new Marker();
        Set<Tag> tags = marker.getTags(pack);
        HttpSession session = request.getSession(false);
        session.setAttribute("tags", tags);
        session.setAttribute("pack", pack);
        response.sendRedirect(request.getContextPath() + "/generated-form.jsp");
    }
}
