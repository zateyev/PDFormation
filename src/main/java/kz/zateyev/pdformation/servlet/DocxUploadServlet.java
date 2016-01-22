package kz.zateyev.pdformation.servlet;

import kz.zateyev.pdformation.entity.Marker;
import kz.zateyev.pdformation.entity.Tag;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.*;
import java.util.Set;

@MultipartConfig
public class DocxUploadServlet extends HttpServlet {
    private String filepath;

    //    private final static Logger LOGGER = Logger.getLogger(DocxUploadServlet.class.getCanonicalName());
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = filePart.getSubmittedFileName();
        InputStream fileContent = filePart.getInputStream();
        filepath = "D:\\tmp2" + File.separator + fileName;
        OutputStream out = new FileOutputStream(new File(filepath));

        //uploading file
        int read;
        final byte[] bytes = new byte[1024];
        while ((read = fileContent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.close();
        fileContent.close();

        //extracting tags from file
        XWPFDocument doc = null;
        doc = new XWPFDocument(new FileInputStream(filepath));
        Marker marker = new Marker();
        Set<Tag> tags = marker.getTags(doc);
        HttpSession session = request.getSession(true);
        session.setAttribute("tags", tags);
        session.setAttribute("filepath", filepath);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/generated-form.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
