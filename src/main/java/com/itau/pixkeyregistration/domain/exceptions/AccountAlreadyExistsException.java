package com.itau.pixkeyregistration.domain.exceptions;

public class AccountAlreadyExistsException extends DomainException {
    public AccountAlreadyExistsException(String type, String message) {
        super(type, message);
    }
}
