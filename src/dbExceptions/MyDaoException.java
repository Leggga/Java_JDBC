package dbExceptions;

import java.io.IOException;

public class MyDaoException extends IOException {
    public MyDaoException(String message) {
        super(message);
    }

    public MyDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
