package kz.zateyev.pdformation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
    View execute(HttpServletRequest request, HttpServletResponse response);
}
