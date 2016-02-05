package kz.zateyev.pdformation.action;

import kz.zateyev.pdformation.dao.DaoFactory;
import kz.zateyev.pdformation.dao.UserDao;
import kz.zateyev.pdformation.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        DaoFactory jdbcDaoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC);
        UserDao jdbcUserDao = jdbcDaoFactory.getUserDao();

        User user = jdbcUserDao.find(email, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            return new View("home", true);
        }

        request.setAttribute("error", "Unknown username/password. Please retry.");
        return new View("login", true);
    }
}
