package com.itau.pixkeyregistration.domain.exceptions;

public class AccountNotFoundException extends DomainException {
    public AccountNotFoundException(String type, String message) {
        super(type, message);
    }
}
