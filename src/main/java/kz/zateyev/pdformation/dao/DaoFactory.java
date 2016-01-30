package kz.zateyev.pdformation.dao;

public abstract class DaoFactory {
    public static final int JDBC = 1;

    public static DaoFactory getDaoFactory(int whichFactory) {
        switch (whichFactory) {
            case JDBC:
                return new JdbcDaoFactory();
            default:
                throw new DaoException("required DaoFactory not found");
        }
    }

    public abstract UserDao getUserDao();
    public abstract PackDao getPackDao();
    public abstract DocumentDao getDocumentDao();
}
