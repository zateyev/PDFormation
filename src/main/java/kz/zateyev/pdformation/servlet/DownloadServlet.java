package kz.zateyev.pdformation.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ResourceBundle;

public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String fileName = request.getParameter("filename");
        String fileType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        response.setContentType(fileType);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
        String filePath = resourceBundle.getString("formed.document.location");

        File myFile = new File(filePath + fileName);
        response.setContentLength((int) myFile.length());
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(myFile);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }
}