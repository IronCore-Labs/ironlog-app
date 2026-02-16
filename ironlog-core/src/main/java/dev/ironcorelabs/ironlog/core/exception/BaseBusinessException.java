package dev.ironcorelabs.ironlog.core.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class BaseBusinessException extends RuntimeException {

    protected BaseBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    protected BaseBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object ...params) {
        this(message, cause, enableSuppression, writableStackTrace);
        this.params = new ArrayList<>(Arrays.asList(params));
    }

    public BaseBusinessException(Throwable cause) {
        super(cause);
    }

    public BaseBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseBusinessException(String message, Throwable cause, Object ...params) {
        this(message, cause);
        this.params = new ArrayList<>(Arrays.asList(params));
    }

    public BaseBusinessException(String message) {
        super(message);
    }

    public BaseBusinessException(String message, Object ...params) {
        this(message);
        this.params = new ArrayList<>(Arrays.asList(params));
    }

    public BaseBusinessException() {
        super();
    }

    public abstract int getHttpStatusCode();

    private List<Object> params = new ArrayList<>();
}
