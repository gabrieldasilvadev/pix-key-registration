package com.itau.pixkeyregistration.domain.exceptions;

import java.util.Map;

public class InvalidAgeException extends DomainException {
    public InvalidAgeException(String type, String message, Map<String, Object> details) {
        super(type, message, details);
    }
}
