package kz.zateyev.pdformation.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URISyntaxException;

@MultipartConfig
public class DocxUploadServlet extends HttpServlet {
//    private final static Logger LOGGER = Logger.getLogger(DocxUploadServlet.class.getCanonicalName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = filePart.getSubmittedFileName();
        InputStream fileContent = filePart.getInputStream();
        OutputStream out = new FileOutputStream(new File("D:\\tmp2" + File.separator + fileName));

        int read;
        final byte[] bytes = new byte[1024];
        while ((read = fileContent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.close();
        fileContent.close();

        PrintWriter writer = response.getWriter();
        writer.println("File " + fileName + " uploaded");
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
