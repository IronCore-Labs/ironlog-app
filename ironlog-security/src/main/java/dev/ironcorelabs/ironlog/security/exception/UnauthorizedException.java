package dev.ironcorelabs.ironlog.security.exception;

import dev.ironcorelabs.ironlog.core.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseBusinessException {

    public UnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... params) {
        super(message, cause, enableSuppression, writableStackTrace, params);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(String message, Throwable cause, Object... params) {
        super(message, cause, params);
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Object... params) {
        super(message, params);
    }

    public UnauthorizedException() {
    }

    @Override
    public int getHttpStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
