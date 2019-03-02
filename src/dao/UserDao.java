package dao;

import dbExceptions.DBSystemException;
import dbExceptions.NotUniqueUser;
import dbExceptions.UserNotFound;

import java.util.List;

public interface UserDao {

    int insert(User user) throws DBSystemException, NotUniqueUser;

    boolean insert(List<User> users) throws DBSystemException, NotUniqueUser;

    boolean deleteById(int id) throws DBSystemException, UserNotFound;

    List<User> selectAll() throws DBSystemException;
}
