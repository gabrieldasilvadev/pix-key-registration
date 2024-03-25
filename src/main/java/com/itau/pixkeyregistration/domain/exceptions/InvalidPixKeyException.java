package com.itau.pixkeyregistration.domain.exceptions;

public class InvalidPixKeyException extends DomainException {
    public InvalidPixKeyException(String type, String message) {
        super(type, message);
    }
}
