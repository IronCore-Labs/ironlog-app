package dev.ironcorelabs.ironlog.core.exception;


import java.util.List;

public class RecordNotFoundException extends BaseBusinessException {

    public RecordNotFoundException() {
    }

    public RecordNotFoundException(String message, Object... params) {
        super(message, params);
    }

    public RecordNotFoundException(String message) {
        super(message);
    }

    public RecordNotFoundException(String message, Throwable cause, Object... params) {
        super(message, cause, params);
    }

    public RecordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecordNotFoundException(Throwable cause) {
        super(cause);
    }

    public RecordNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... params) {
        super(message, cause, enableSuppression, writableStackTrace, params);
    }

    public RecordNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int getHttpStatusCode() {
        return 404;
    }
}
