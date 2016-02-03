package kz.zateyev.pdformation.dao;

import kz.zateyev.pdformation.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static kz.zateyev.pdformation.dao.JdbcDaoFactory.createConnection;
import static kz.zateyev.pdformation.dao.JdbcDaoFactory.freeConnection;

public class JdbcUserDao implements UserDao {
    public User findById(Long id) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection = createConnection();
        try {
            preparedStatement = connection.prepareStatement("SELECT ID, FIRSTNAME, EMAIL, PASSWORD FROM USER WHERE ID = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            boolean found = resultSet.next();
            if (!found) return null;
            User user = new User();
            user.setId(id);
            user.setFirstName(resultSet.getString("FIRSTNAME"));
            user.setEmail(resultSet.getString("EMAIL"));
            user.setPassword(resultSet.getString("PASSWORD"));
            return user;
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            freeConnection(connection);
        }
    }

    public User findByEmail(String email) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection = createConnection();
        try {
            preparedStatement = connection.prepareStatement("SELECT ID, FIRSTNAME, EMAIL, PASSWORD FROM USER WHERE EMAIL = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            boolean found = resultSet.next();
            if (!found) return null;
            User user = new User();
            user.setId(resultSet.getLong("ID"));
            user.setFirstName(resultSet.getString("FIRSTNAME"));
            user.setEmail(resultSet.getString("EMAIL"));
            user.setPassword(resultSet.getString("PASSWORD"));
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            freeConnection(connection);
        }
    }

    public void update(User user) {
    }

    public User save(User user) {
        return null;
    }

    public User merge(User user) {
        return null;
    }

    public User insert(User user) {
        Connection connection = createConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO USER (ID, FIRSTNAME, EMAIL, PASSWORD) VALUES (DEFAULT, ?, ?, ?)");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            Long id = generatedKeys.getLong(1);
            user.setId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    public boolean remove(User user) {
        return false;
    }

    public boolean removeById(long id) {
        return false;
    }
}
