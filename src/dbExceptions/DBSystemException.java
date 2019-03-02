package dbExceptions;

public class DBSystemException extends MyDaoException {

    public DBSystemException(String message) {
        super(message);
    }

    public DBSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
