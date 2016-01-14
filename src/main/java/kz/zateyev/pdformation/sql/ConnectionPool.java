package kz.zateyev.pdformation.sql;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static ConnectionPool instance;
    private String driverName;
    private List<Connection> freeConnections = new ArrayList<>();
    private String url;
    private String user;
    private String password;
    private int maxConnection;

    private ConnectionPool(String driverName, String url, String user, String password, int maxConnection) {
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxConnection = maxConnection;
        loadDrivers();
    }

    public static synchronized ConnectionPool getInstance(String DRIVER_NAME, String url, String user, String password, int maxConnection) {
        if (instance == null) {
            instance = new ConnectionPool(DRIVER_NAME, url, user, password, maxConnection);
        }
        return instance;
    }

    private void loadDrivers() {
        try {
            Driver driver = (Driver) Class.forName(this.driverName).newInstance();
            DriverManager.registerDriver(driver);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            throw new ConnectionPoolException();
        }
    }

    public synchronized Connection getConnection() {
        Connection connection;
        if (!freeConnections.isEmpty()) {
            connection = freeConnections.get(freeConnections.size() - 1);
            freeConnections.remove(connection);
            try {
                if (connection.isClosed()) {
                    connection = getConnection();
                }
            } catch (SQLException e) {
                throw new ConnectionPoolException();
            }
        } else {
            connection = newConnection();
        }
        return connection;
    }

    private Connection newConnection() {
        Connection connection;
        try {
            if (user == null) {
                connection = DriverManager.getConnection(url);
            } else {
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException();
        }
        return connection;
    }

    public synchronized void freeConnection(Connection connection) {
        if (connection != null && (freeConnections.size() <= maxConnection)) {
            freeConnections.add(connection);
        }
    }

    public synchronized void release() {
        for (Connection connection : freeConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ConnectionPoolException("Can't close connection for pool", e);
            }
        }
        freeConnections.clear();
    }
}
