package implConnectionFactory;

import dbExceptions.DBSystemException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryJdbc implements ConnectionFactory {
    private static final String URL_DB;
    private static final String LOGIN;
    private static final String PASSWORD;

    static {
        Properties prop = null;
        try {
            prop = ConnectionFactory.getDBConfiguration();
        } catch (DBSystemException e) {
            e.printStackTrace();
        }

        URL_DB = prop.getProperty("dbUrl");
        LOGIN = prop.getProperty("dbLogin");
        PASSWORD = prop.getProperty("dbPassword");
    }

    @Override
    public Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(URL_DB, LOGIN, PASSWORD);
    }

    @Override
    public void close() throws SQLException {
        // just a  stub
    }

}
