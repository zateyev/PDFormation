package kz.zateyev.pdformation.dao;

import kz.zateyev.pdformation.entity.Pack;
import kz.zateyev.pdformation.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static kz.zateyev.pdformation.dao.JdbcDaoFactory.createConnection;
import static kz.zateyev.pdformation.dao.JdbcDaoFactory.freeConnection;

public class JdbcPackDao implements PackDao {
    @Override
    public Pack findById(Long id) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection = createConnection();
        try {
            preparedStatement = connection.prepareStatement("SELECT NAME, ID_USER, LOCATION FROM PACK WHERE ID=?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Pack pack = new Pack();
            while (resultSet.next()) {
                pack.setId(id);
                pack.setName(resultSet.getString("NAME"));
                pack.setLocation(resultSet.getString("LOCATION"));
            }
            return pack;
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            freeConnection(connection);
        }
    }

    @Override
    public Pack findByName(String name) {
        return null;
    }

    @Override
    public List<Pack> findByUser(User user) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection = createConnection();
        try {
            preparedStatement = connection.prepareStatement("SELECT ID, NAME, LOCATION FROM PACK WHERE ID_USER=?");
            preparedStatement.setLong(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            List<Pack> packs = new ArrayList<>();
            while (resultSet.next()) {
                Pack pack = new Pack();
                pack.setId(resultSet.getLong("ID"));
                pack.setName(resultSet.getString("NAME"));
                pack.setLocation(resultSet.getString("LOCATION"));
                pack.setUser(user);
                packs.add(pack);
            }
            return packs;
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            freeConnection(connection);
        }
    }

    @Override
    public void update(Pack pack) {
        Connection connection = createConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE PACK SET NAME=?, ID_USER=?, LOCATION=? WHERE ID=?");
            preparedStatement.setString(1, pack.getName());
            preparedStatement.setLong(2, pack.getUser().getId());
            preparedStatement.setString(3, pack.getLocation());
            preparedStatement.setLong(4, pack.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Pack save(Pack pack) {
        return null;
    }

    @Override
    public Pack insert(Pack pack) {
        Connection connection = createConnection();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO PACK (ID, NAME, ID_USER, LOCATION) VALUES (DEFAULT, ?, ?, ?)");
            preparedStatement.setString(1, pack.getName());
            preparedStatement.setLong(2, pack.getUser().getId());
            preparedStatement.setString(3, pack.getLocation());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            Long id = generatedKeys.getLong(1);
            pack.setId(id);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return pack;
    }

    @Override
    public boolean remove(Pack pack) {
        return false;
    }

    @Override
    public boolean removeById(Long id) {
        DaoFactory jdbcDaoFactory = DaoFactory.getDaoFactory(DaoFactory.JDBC);
        DocumentDao jdbcDocumentDao = jdbcDaoFactory.getDocumentDao();
        Connection connection = createConnection();
        PreparedStatement preparedStatement;
        jdbcDocumentDao.removeByPackId(id);
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM PACK WHERE ID=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
