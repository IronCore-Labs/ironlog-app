package dev.ironcorelabs.ironlog.security.exception;

import dev.ironcorelabs.ironlog.core.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class AbsentTokenException extends BaseBusinessException {

    public AbsentTokenException() {
        super();
    }

    public AbsentTokenException(String message) {
        super(message);
    }

    public AbsentTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbsentTokenException(Throwable cause) {
        super(cause);
    }

    protected AbsentTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int getHttpStatusCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
