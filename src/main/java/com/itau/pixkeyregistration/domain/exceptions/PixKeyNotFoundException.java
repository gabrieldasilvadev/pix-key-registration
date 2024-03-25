package com.itau.pixkeyregistration.domain.exceptions;

public class PixKeyNotFoundException extends DomainException {
    public PixKeyNotFoundException(String type, String message) {
        super(type, message);
    }
}
