package kz.zateyev.pdformation.servlet;

import kz.zateyev.pdformation.dao.DaoFactory;
import kz.zateyev.pdformation.dao.PackDao;
import kz.zateyev.pdformation.dao.UserDao;
import kz.zateyev.pdformation.entity.Pack;
import kz.zateyev.pdformation.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SignInServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        DaoFactory jdbcDaoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC);
        UserDao jdbcUserDao = jdbcDaoFactory.getUserDao();
        User user = jdbcUserDao.findByEmail(email);
        if (user.getPassword().equals(password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            PackDao jdbcPackDao = jdbcDaoFactory.getPackDao();
            List<Pack> packs = jdbcPackDao.findByUser(user);
            session.setAttribute("packs", packs);
            response.sendRedirect(request.getContextPath() + "/my-packs.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
