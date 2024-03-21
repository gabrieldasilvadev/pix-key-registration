package com.itau.pixkeyregistration.domain.exceptions;

public class InvalidPasswordException extends DomainException{
    public InvalidPasswordException(String type, String message) {
        super(type, message);
    }
}
