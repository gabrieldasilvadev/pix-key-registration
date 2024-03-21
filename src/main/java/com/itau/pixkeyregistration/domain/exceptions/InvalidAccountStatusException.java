package com.itau.pixkeyregistration.domain.exceptions;

import java.util.Map;

public class InvalidAccountStatusException extends DomainException {
    public InvalidAccountStatusException(String type, String message, Map<String, Object> details) {
        super(type, message, details);
    }
}
