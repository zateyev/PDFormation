package kz.zateyev.pdformation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return new View("index", true);
    }
}
