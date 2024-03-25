package com.itau.pixkeyregistration.domain.exceptions;

public class InvalidRandomPixKeyException extends DomainException {
    public InvalidRandomPixKeyException(String type, String message) {
        super(type, message);
    }
}
