package dev.ironcorelabs.ironlog.security.exception;

import dev.ironcorelabs.ironlog.core.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class SessionAlreadyRevokedException extends BaseBusinessException {

    protected SessionAlreadyRevokedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    protected SessionAlreadyRevokedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... params) {
        super(message, cause, enableSuppression, writableStackTrace, params);
    }

    public SessionAlreadyRevokedException(Throwable cause) {
        super(cause);
    }

    public SessionAlreadyRevokedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionAlreadyRevokedException(String message, Throwable cause, Object... params) {
        super(message, cause, params);
    }

    public SessionAlreadyRevokedException(String message) {
        super(message);
    }

    public SessionAlreadyRevokedException(String message, Object... params) {
        super(message, params);
    }

    public SessionAlreadyRevokedException() {
        super();
    }

    @Override
    public int getHttpStatusCode() {
        return HttpStatus.UNPROCESSABLE_ENTITY.value();
    }
}
