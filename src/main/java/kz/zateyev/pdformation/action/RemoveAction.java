package kz.zateyev.pdformation.action;

import kz.zateyev.pdformation.dao.DaoFactory;
import kz.zateyev.pdformation.dao.DocumentDao;
import kz.zateyev.pdformation.dao.PackDao;
import kz.zateyev.pdformation.entity.Document;
import kz.zateyev.pdformation.entity.Pack;
import kz.zateyev.pdformation.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RemoveAction implements Action {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) {
        Long packID = Long.valueOf(request.getParameter("packid"));
        DaoFactory jdbcDaoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC);
        PackDao jdbcPackDao = jdbcDaoFactory.getPackDao();
        DocumentDao jdbcDocumentDao = jdbcDaoFactory.getDocumentDao();
        Pack pack = jdbcPackDao.findById(packID);
        List<Document> documents = jdbcDocumentDao.findByPack(pack);
        for (Document document : documents) {
            document.delete();
        }
        jdbcPackDao.removeById(packID);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Pack> packs = jdbcPackDao.findByUser(user);
        session.setAttribute("packs", packs);
        return new View("my-packs", false);
    }
}
