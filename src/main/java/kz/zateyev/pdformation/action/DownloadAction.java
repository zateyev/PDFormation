package kz.zateyev.pdformation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ResourceBundle;

public class DownloadAction implements Action {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("filename");
        String fileType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        response.setContentType(fileType);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
        String filePath = resourceBundle.getString("formed.document.location");

        File myFile = new File(filePath + fileName);
        response.setContentLength((int) myFile.length());
        try (OutputStream out = response.getOutputStream()) {
            FileInputStream in = new FileInputStream(myFile);
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new View("formed-document", false);
    }
}
