package dev.ironcorelabs.ironcore.training.service.impl;

import dev.ironcorelabs.ironlog.core.exception.BaseBusinessException;
import org.springframework.http.HttpStatus;

public class ClientAlreadyAssignedException extends BaseBusinessException {

    public ClientAlreadyAssignedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ClientAlreadyAssignedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... params) {
        super(message, cause, enableSuppression, writableStackTrace, params);
    }

    public ClientAlreadyAssignedException(Throwable cause) {
        super(cause);
    }

    public ClientAlreadyAssignedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientAlreadyAssignedException(String message, Throwable cause, Object... params) {
        super(message, cause, params);
    }

    public ClientAlreadyAssignedException(String message) {
        super(message);
    }

    public ClientAlreadyAssignedException(String message, Object... params) {
        super(message, params);
    }

    public ClientAlreadyAssignedException() {
    }

    @Override
    public int getHttpStatusCode() {
        return HttpStatus.CONFLICT.value();
    }
}
