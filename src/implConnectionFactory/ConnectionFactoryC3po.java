package implConnectionFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import dbExceptions.DBSystemException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactoryC3po implements ConnectionFactory {
    private static final String URL_DB;
    private static final String LOGIN;
    private static final String PASSWORD;
    private ComboPooledDataSource cpds;

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

    public ConnectionFactoryC3po(){
        cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl(URL_DB);
        cpds.setUser(LOGIN);
        cpds.setPassword(PASSWORD);

        cpds.setMinPoolSize(10);
        cpds.setInitialPoolSize(20);
        cpds.setAcquireIncrement(50);
        cpds.setMaxPoolSize(50);
        cpds.setMaxStatements(50);
        cpds.setIdleConnectionTestPeriod(60);
    }

    @Override
    public Connection getNewConnection() throws SQLException {
        return cpds.getConnection();
    }

    @Override
    public void close() throws SQLException {
        cpds.close();
    }
}
