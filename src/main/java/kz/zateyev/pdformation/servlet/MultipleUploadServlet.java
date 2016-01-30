package kz.zateyev.pdformation.servlet;

import kz.zateyev.pdformation.dao.DaoFactory;
import kz.zateyev.pdformation.dao.DocumentDao;
import kz.zateyev.pdformation.dao.JdbcPackDao;
import kz.zateyev.pdformation.dao.PackDao;
import kz.zateyev.pdformation.entity.Document;
import kz.zateyev.pdformation.entity.Pack;
import kz.zateyev.pdformation.entity.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class MultipleUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String packName = "Hosting"; //request.getParameter("packname");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
        String filepath = resourceBundle.getString("upload.location");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        PrintWriter out = response.getWriter();
        File path = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
        } else {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = null;
            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            Iterator itr = items.iterator();
            Pack initPack = new Pack();
            initPack.setName(packName);
            initPack.setUser(user);
            initPack.setLocation(filepath + File.separator);
            DaoFactory jdbcDaoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC);
            PackDao jdbcPackDao = jdbcDaoFactory.getPackDao();
            Pack pack = jdbcPackDao.insert(initPack);
            DocumentDao jdbcDocumentDao = jdbcDaoFactory.getDocumentDao();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                } else {
                    try {
                        String itemName = item.getName();
                        File uploadedFile = new File(filepath + itemName);
                        item.write(uploadedFile);
                        XWPFDocument docx = new XWPFDocument(new FileInputStream(filepath + itemName));
                        Document document = new Document(docx, itemName);
                        document.setPack(pack);
                        jdbcDocumentDao.insert(document);
                        out.println("<table><tr><td><b>Your file has been saved at the loaction:</b></td></tr><tr><td><b>" + filepath + itemName + "</td></tr></table>");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
