package com.itau.pixkeyregistration.domain.exceptions;

public class AccountLimitExceededException extends DomainException {
    public AccountLimitExceededException(String type, String message) {
        super(type, message);
    }
}
