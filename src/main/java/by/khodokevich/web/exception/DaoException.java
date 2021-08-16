package by.khodokevich.web.exception;

/**
 * DaoException is exception is invoked in Dao layer.
 *
 * @author Oleg Khodokevich
 *
 */
public class DaoException extends Exception{
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
