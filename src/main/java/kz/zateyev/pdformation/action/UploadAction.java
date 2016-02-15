package kz.zateyev.pdformation.action;

import kz.zateyev.pdformation.dao.DaoFactory;
import kz.zateyev.pdformation.dao.DocumentDao;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class UploadAction implements Action {
    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) {
        DaoFactory jdbcDaoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC);
        PackDao jdbcPackDao = jdbcDaoFactory.getPackDao();
        String packName = "Hosting"; //request.getParameter("packname");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
        String filepath = resourceBundle.getString("upload.location");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
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
            initPack.setLocation(filepath);
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        List<Pack> packs = jdbcPackDao.findByUser(user);
        session.setAttribute("packs", packs);
        return new View("my-packs", true);
    }
}
