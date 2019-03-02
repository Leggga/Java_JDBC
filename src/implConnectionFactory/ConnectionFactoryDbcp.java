package implConnectionFactory;

import org.apache.commons.dbcp.BasicDataSource;
import dbExceptions.DBSystemException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryDbcp implements ConnectionFactory {
    private static final String URL_DB;
    private static final String LOGIN;
    private static final String PASSWORD;
    private final BasicDataSource ds;

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

    public ConnectionFactoryDbcp() {
        ds = new BasicDataSource();
        ds.setUrl(URL_DB);
        ds.setUsername(LOGIN);
        ds.setPassword(PASSWORD);

        ds.setMaxIdle(20);
        ds.setMinIdle(5);
        ds.setMaxOpenPreparedStatements(180);
    }

    @Override
    public Connection getNewConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public void close() throws SQLException {
        ds.close();
    }
}
