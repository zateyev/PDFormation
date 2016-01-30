package kz.zateyev.pdformation.dao;

import kz.zateyev.pdformation.entity.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static kz.zateyev.pdformation.dao.JdbcDaoFactory.createConnection;

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
