package implConnectionFactory;

import dbExceptions.DBSystemException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface ConnectionFactory {

    Connection getNewConnection() throws SQLException;

    void close() throws SQLException;

    static Properties getDBConfiguration() throws DBSystemException{
        Properties prop = new Properties();

        try(InputStream input = new FileInputStream("src//config.properties")){
            prop.load(input);
        }catch (IOException ex){
            throw new DBSystemException("Can't find a database configuration file");
        }
        return prop;
    }
}
