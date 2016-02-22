package kz.zateyev.pdformation.action;

import kz.zateyev.pdformation.dao.DaoFactory;
import kz.zateyev.pdformation.dao.UserDao;
import kz.zateyev.pdformation.entity.User;
import kz.zateyev.pdformation.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterAction implements Action {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean loginValid = Validator.isLoginValid(email.trim());
        if (!loginValid) {
            HttpSession session = request.getSession();
            session.setAttribute("emailError", "emailError");
            return new View("register", false);
        }

        User user = new User(firstName, email, password);
        DaoFactory jdbcDaoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC);
        UserDao jdbcUserDao = jdbcDaoFactory.getUserDao();
        jdbcUserDao.insert(user);
        return new View("login", true);
    }
}
