package com.itau.pixkeyregistration.domain.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class DomainException extends RuntimeException {
    private final String type;
    private final Map<String, Object> details;

    public DomainException(String type, String message) {
        super(message);
        this.type = type;
        this.details = null;
    }

    public DomainException(String type, String message, Throwable cause, Map<String, Object> details) {
        super(message, cause);
        this.type = type;
        this.details = details;
    }

    public DomainException(String type, String message, Map<String, Object> details) {
        super(message);
        this.type = type;
        this.details = details;
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
        this.type = "INTERNAL_ERROR";
        this.details = null;
    }
}
