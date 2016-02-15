package kz.zateyev.pdformation.action;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {
    private Map<String, Action> actions;

    public ActionFactory() {
        actions = new HashMap<>();
        actions.put("POST/register", new RegisterAction());
        actions.put("POST/login", new LoginAction());
        actions.put("POST/filler", new FillerAction());
        actions.put("POST/upload", new UploadAction());
        actions.put("GET/form", new FormGeneratorAction());
        actions.put("GET/logout", new LogoutAction());
        actions.put("GET/download", new DownloadAction());
        actions.put("GET/remove", new RemoveAction());
    }

    public Action getAction(HttpServletRequest request) {
        String method = request.getMethod();
        String action = request.getRequestURI().substring(request.getContextPath().length());
        return actions.get(method + action);
    }
}
