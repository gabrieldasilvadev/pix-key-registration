package com.itau.pixkeyregistration.domain.exceptions;

public class PersonAlreadyExistsException extends DomainException {
    public PersonAlreadyExistsException(String type, String message) {
        super(type, message);
    }
}
