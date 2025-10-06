package ru.yandex.practicum.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {
    private final Throwable cause;
    private final StackTraceElement[] stackTrace;
    private final HttpStatus httpStatus;
    private final String userMessage;
    private final String message;
    private final Throwable[] suppressed;
    private final String localizedMessage;

    public ApiError(HttpStatus httpStatus, Exception e, String userMessage) {
        this.cause = e.getCause();
        this.stackTrace = e.getStackTrace();
        this.httpStatus = httpStatus;
        this.userMessage = userMessage;
        this.message = e.getMessage();
        this.suppressed = e.getSuppressed();
        this.localizedMessage = e.getLocalizedMessage();
    }
}