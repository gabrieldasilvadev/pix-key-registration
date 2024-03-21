package com.itau.pixkeyregistration.domain.exceptions;

public class InvalidEmailException extends DomainException {
    public InvalidEmailException(String type, String message) {
        super(type, message);
    }
}
