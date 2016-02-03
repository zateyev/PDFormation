package kz.zateyev.pdformation.dao;

import kz.zateyev.pdformation.entity.Document;
import kz.zateyev.pdformation.entity.Pack;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.print.Doc;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static kz.zateyev.pdformation.dao.JdbcDaoFactory.createConnection;
import static kz.zateyev.pdformation.dao.JdbcDaoFactory.freeConnection;

public class JdbcDocumentDao implements DocumentDao {
    @Override
    public Document findById(Long id) {
        return null;
    }

    @Override
    public Document findByName(String name) {
        return null;
    }

    @Override
    public List<Document> findByPack(Pack pack) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
        String filepath = resourceBundle.getString("upload.location");
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection = createConnection();
        try {
            preparedStatement = connection.prepareStatement("SELECT ID, NAME FROM DOCUMENT WHERE PACK_ID=?");
            preparedStatement.setLong(1, pack.getId());
            resultSet = preparedStatement.executeQuery();
            List<Document> documents = new ArrayList<>();
            while (resultSet.next()) {
                String docName = resultSet.getString("NAME");
                XWPFDocument docx = null;
                try {
                    docx = new XWPFDocument(new FileInputStream(filepath + docName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Document document = new Document(docx, docName);
                document.setId(resultSet.getLong("ID"));
                document.setPack(pack);
                documents.add(document);
            }
            return documents;
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            freeConnection(connection);
        }
    }

    @Override
    public Document insert(Document document) {
        Connection connection = createConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO DOCUMENT (ID, NAME, PACK_ID) VALUES (DEFAULT, ?, ?)");
            preparedStatement.setString(1, document.getName());
            preparedStatement.setLong(2, document.getPack().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            Long id = generatedKeys.getLong(1);
            document.setId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return document;
    }

    @Override
    public boolean remove(Document document) {
        return false;
    }

    @Override
    public boolean removeById(Long id) {
        return false;
    }
}
