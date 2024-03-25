package com.itau.pixkeyregistration.domain.exceptions;

public class PixKeyAlreadyInactivatedException extends DomainException {
    public PixKeyAlreadyInactivatedException(String type, String message) {
        super(type, message);
    }
}
