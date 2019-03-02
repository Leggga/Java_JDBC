package dbExceptions;

public class NotUniqueUser extends MyDaoException {

    public NotUniqueUser(String message) {
        super(message);
    }

    public NotUniqueUser(String message, Throwable cause) {
        super(message, cause);
    }
}
