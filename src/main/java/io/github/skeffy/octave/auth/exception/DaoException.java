package io.github.skeffy.octave.auth.exception;

public class DaoException extends RuntimeException{
    public DaoException() {
        super();
    }
    public DaoException(String message) {
        super(message);
    }
    public DaoException(String message, Exception cause) {
        super(message, cause);
    }
}
