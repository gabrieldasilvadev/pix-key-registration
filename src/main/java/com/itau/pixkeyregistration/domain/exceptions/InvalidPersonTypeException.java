package com.itau.pixkeyregistration.domain.exceptions;

import java.util.Map;

public class InvalidPersonTypeException extends DomainException{
    public InvalidPersonTypeException(String type, String message, Map<String, Object> details) {
        super(type, message, details);
    }

    public InvalidPersonTypeException(String type, String message) {
        super(type, message);
    }
}
