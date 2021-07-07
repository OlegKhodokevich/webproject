package by.khodokevich.web.exception;

public class PoolConnectionException extends Exception{
    public PoolConnectionException() {
    }

    public PoolConnectionException(String message) {
        super(message);
    }

    public PoolConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolConnectionException(Throwable cause) {
        super(cause);
    }
}
