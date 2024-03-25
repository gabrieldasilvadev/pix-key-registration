package com.itau.pixkeyregistration.domain.exceptions;

public class AccountNumberExceededLimit extends DomainException {
    public AccountNumberExceededLimit(String type, String message) {
        super(type, message);
    }
}
