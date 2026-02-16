package dev.ironcorelabs.ironlog.core.exception;

import dev.ironcorelabs.ironlog.restapi.openapi.model.Error;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Optional;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<Error> handleBusiness(BaseBusinessException ex, HttpServletRequest request) {
        final HttpStatus status = Optional.ofNullable(HttpStatus.resolve(ex.getHttpStatusCode()))
                .orElse(HttpStatus.BAD_REQUEST);
        return buildResponse(status, ex.getLocalizedMessage(), ex.getParams(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "internal.server.error", List.of(), request.getRequestURI());
    }

    protected ResponseEntity<Error> buildResponse(HttpStatus status, String message
            , List<Object> params, String url) {

        final String translated = messageSource.getMessage(message, params.toArray()
                , LocaleContextHolder.getLocale());

        final Error errorResponse = new Error()
                .message(translated)
                .url(url)
                .statusCode(status.value())
                .status(status.getReasonPhrase());

        return ResponseEntity.status(status).body(errorResponse);
    }
}
