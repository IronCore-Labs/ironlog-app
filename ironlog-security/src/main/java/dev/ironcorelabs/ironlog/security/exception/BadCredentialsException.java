package dev.ironcorelabs.ironlog.security.exception;

import dev.ironcorelabs.ironlog.core.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class BadCredentialsException extends BaseBusinessException {

    public BadCredentialsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BadCredentialsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... params) {
        super(message, cause, enableSuppression, writableStackTrace, params);
    }

    public BadCredentialsException(Throwable cause) {
        super(cause);
    }

    public BadCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadCredentialsException(String message, Throwable cause, Object... params) {
        super(message, cause, params);
    }

    public BadCredentialsException(String message) {
        super(message);
    }

    public BadCredentialsException(String message, Object... params) {
        super(message, params);
    }

    public BadCredentialsException() {
    }

    @Override
    public int getHttpStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
