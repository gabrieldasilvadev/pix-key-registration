package com.itau.pixkeyregistration.domain.exceptions;

public class InvalidPersonNameException extends DomainException{
    public InvalidPersonNameException(String type, String message) {
        super(type, message);
    }
}
