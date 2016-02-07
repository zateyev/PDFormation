package kz.zateyev.pdformation.action;

import kz.zateyev.pdformation.dao.DaoFactory;
import kz.zateyev.pdformation.dao.DocumentDao;
import kz.zateyev.pdformation.dao.PackDao;
import kz.zateyev.pdformation.entity.Document;
import kz.zateyev.pdformation.entity.Marker;
import kz.zateyev.pdformation.entity.Pack;
import kz.zateyev.pdformation.entity.Tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

public class FormGeneratorAction implements Action {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) {
        Long packID = Long.valueOf(request.getParameter("packid"));
        DaoFactory jdbcDaoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC);
        PackDao jdbcPackDao = jdbcDaoFactory.getPackDao();
        DocumentDao jdbcDocumentDao = jdbcDaoFactory.getDocumentDao();
        Pack pack = jdbcPackDao.findById(packID);
        List<Document> documents = jdbcDocumentDao.findByPack(pack);
        pack.setDocuments(documents);
        Marker marker = new Marker();
        Set<Tag> tags = marker.getTags(pack);
        HttpSession session = request.getSession();
        session.setAttribute("tags", tags);
        session.setAttribute("pack", pack);
        return new View("generated-form", true);
    }
}
