package com.itau.pixkeyregistration.domain.exceptions;

import java.util.Map;

public class InvalidAccountTypeException extends DomainException {
    public InvalidAccountTypeException(String type, String message, Map<String, Object> details) {
        super(type, message, details);
    }
}
