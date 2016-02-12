package kz.zateyev.pdformation.servlet;

import kz.zateyev.pdformation.action.Action;
import kz.zateyev.pdformation.action.ActionFactory;
import kz.zateyev.pdformation.action.View;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FrontControllerServlet", urlPatterns = {"/register", "/login", "/logout", "/form", "/filler", "/download", "/upload"})
public class FrontControllerServlet extends HttpServlet {
    private ActionFactory factory;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        factory = new ActionFactory();
        Action action = factory.getAction(req);
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setCharacterEncoding("UTF-8");
        View view = action.execute(req, resp);
        if (view.isRedirect())
            resp.sendRedirect(req.getContextPath() + "/" + view.getPath() + ".jsp");
        else
            req.getRequestDispatcher("/" + view.getPath() + ".jsp").forward(req, resp);
    }

    @Override
    public void init() throws ServletException {
        factory = new ActionFactory();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
