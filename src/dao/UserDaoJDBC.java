package dao;

import dbExceptions.DBSystemException;
import implConnectionFactory.ConnectionFactory;
import implConnectionFactory.ConnectionFactoryFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC implements UserDao {

    private final String SELECT_ALL_SQL = "SELECT * FROM User";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM User WHERE id = ?";
    private final String SELECT_BY_LOGIN_SQL = "SELECT * FROM User WHERE login = ?";
    private final String SELECT_BY_EMAIL_SQL = "SELECT * FROM User WHERE email = ?";
    private final String INSERT_SQL = "INSERT INTO User (id, email, login, password) VALUES(?, ?, ?, ?)" ;
    private final String DELETE_BY_ID_SQL = "DELETE FROM User WHERE id = ?";

    ConnectionFactory connectionFactory = ConnectionFactoryFactory.newConnectionFactory();

    @Override
    public List<User> selectAll() throws DBSystemException {
        List<User> users;
        ResultSet setUsers;
        Connection conn = null;
        Statement statement = null;

        try {
            conn = connectionFactory.getNewConnection();
            statement = conn.createStatement();
            setUsers = statement.executeQuery(SELECT_ALL_SQL);

            users = new ArrayList<>();

            while(setUsers.next()){
                int id = setUsers.getInt(1);
                String email = setUsers.getString(2);
                String login = setUsers.getString(3);
                String password = setUsers.getString(4);

                users.add(new User(id,email,login,password));
            }
        } catch (SQLException e) {
            JdbcUtils.rollBackImmediately(conn);
            throw new DBSystemException("Can`t execute SQL query (" + SELECT_ALL_SQL + ") " + e.getMessage());
        }finally {
            JdbcUtils.closeResource(statement);
            JdbcUtils.closeResource(conn);
        }

        return users;
    }

    @Override
    public boolean deleteById(int id) throws DBSystemException {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = connectionFactory.getNewConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(DELETE_BY_ID_SQL);
            statement.setInt(1,id);
            flag = statement.executeUpdate() != 0;
            conn.commit();
        } catch (SQLException e) {
            JdbcUtils.rollBackImmediately(conn);
            throw new DBSystemException("Can`t execute SQL query (" + DELETE_BY_ID_SQL + ")");
        }finally {
            JdbcUtils.closeResource(statement);
            JdbcUtils.closeResource(conn);
        }

        return flag;
    }

    @Override
    public int insert(User user) throws DBSystemException {
        Connection conn = null;
        PreparedStatement statement = null;
        int result = -1 ;

        try {
            conn = connectionFactory.getNewConnection();
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(INSERT_SQL,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,user.getId());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getLogin());
            statement.setString(4,user.getPassword());
            statement.executeUpdate();

            ResultSet key = statement.getGeneratedKeys();
            if ( key.next()){
                result = key.getInt(1);
            }

            conn.commit();
        } catch (SQLException e) {
            JdbcUtils.rollBackImmediately(conn);
            throw new DBSystemException("Can`t execute SQL query (" + INSERT_SQL + ")");
        }finally {
            JdbcUtils.closeResource(statement);
            JdbcUtils.closeResource(conn);
        }
        return result;
    }

    @Override
     public boolean insert(List<User> users) throws DBSystemException {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = connectionFactory.getNewConnection();
            statement = conn.prepareStatement(INSERT_SQL);

            for (User user : users){
                statement.setString(1,user.getEmail());
                statement.setString(2,user.getLogin());
                statement.setString(3,user.getPassword());
                statement.addBatch();
            }
            statement.executeBatch();
            flag = true;
        } catch (SQLException e) {
            JdbcUtils.rollBackImmediately(conn);
            throw new DBSystemException("Can`t execute SQL query (" + INSERT_SQL + ")");
        }finally {
            JdbcUtils.closeResource(statement);
            JdbcUtils.closeResource(conn);
        }

        return flag;
    }

}
