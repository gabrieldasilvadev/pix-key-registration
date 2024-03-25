package com.itau.pixkeyregistration.domain.exceptions;

public class PixKeyAlreadyRegisteredException extends DomainException {
    public PixKeyAlreadyRegisteredException(String type, String message) {
        super(type, message);
    }
}
