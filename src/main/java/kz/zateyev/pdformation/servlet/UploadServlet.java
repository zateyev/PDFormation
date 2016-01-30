package kz.zateyev.pdformation.servlet;

import kz.zateyev.pdformation.entity.Document;
import kz.zateyev.pdformation.entity.Marker;
import kz.zateyev.pdformation.entity.Tag;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.*;
import java.util.ResourceBundle;
import java.util.Set;

@MultipartConfig
public class UploadServlet extends HttpServlet {
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
    private String filepath = resourceBundle.getString("upload.location");

    //    private final static Logger LOGGER = Logger.getLogger(UploadServlet.class.getCanonicalName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = filePart.getSubmittedFileName();
        InputStream fileContent = filePart.getInputStream();

        OutputStream out = new FileOutputStream(new File(filepath + fileName));

        //uploading file
        int read;
        final byte[] bytes = new byte[1024];
        while ((read = fileContent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.close();
        fileContent.close();

        //extracting tags from file
        XWPFDocument doc = new XWPFDocument(new FileInputStream(filepath + fileName));
        Document document = new Document(doc, fileName);
        Marker marker = new Marker();
        Set<Tag> tags = marker.getTags(document);
        HttpSession session = request.getSession(true);
        session.setAttribute("tags", tags);
        session.setAttribute("filepath", filepath);
        session.setAttribute("fileName", fileName);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/generated-form.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
