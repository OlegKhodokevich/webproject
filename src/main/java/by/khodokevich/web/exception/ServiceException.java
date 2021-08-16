package by.khodokevich.web.exception;

/**
 * ServiceException is exception is invoked in service layer and exit from it.
 *
 * @author Oleg Khodokevich
 *
 */
public class ServiceException extends Exception{
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
