package dev.ironcorelabs.ironlog.core.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends BaseBusinessException {

    public AccessDeniedException() {
        super();
    }

    public AccessDeniedException(String message, Object... params) {
        super(message, params);
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause, Object... params) {
        super(message, cause, params);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(Throwable cause) {
        super(cause);
    }

    protected AccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... params) {
        super(message, cause, enableSuppression, writableStackTrace, params);
    }

    protected AccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int getHttpStatusCode() {
        return HttpStatus.FORBIDDEN.value();
    }
}
