package dev.ironcorelabs.ironlog.security.exception;

import dev.ironcorelabs.ironlog.core.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class InvalidToken extends BaseBusinessException {

    public InvalidToken() {
        super();
    }

    public InvalidToken(String message) {
        super(message);
    }

    public InvalidToken(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidToken(Throwable cause) {
        super(cause);
    }

    protected InvalidToken(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int getHttpStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
