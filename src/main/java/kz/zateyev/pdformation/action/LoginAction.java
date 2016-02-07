package kz.zateyev.pdformation.action;

import kz.zateyev.pdformation.dao.DaoFactory;
import kz.zateyev.pdformation.dao.PackDao;
import kz.zateyev.pdformation.dao.UserDao;
import kz.zateyev.pdformation.entity.Pack;
import kz.zateyev.pdformation.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginAction implements Action {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        DaoFactory jdbcDaoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC);
        UserDao jdbcUserDao = jdbcDaoFactory.getUserDao();
        PackDao jdbcPackDao = jdbcDaoFactory.getPackDao();

        User user = jdbcUserDao.find(email, password);
        List<Pack> packs = jdbcPackDao.findByUser(user);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("packs", packs);
            return new View("my-packs", true);
        }

        request.setAttribute("error", "Unknown username/password. Please retry.");
        return new View("index", true);
    }
}
