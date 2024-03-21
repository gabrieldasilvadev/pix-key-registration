package com.itau.pixkeyregistration.domain.exceptions;

public class PersonNotFoundException extends DomainException{
    public PersonNotFoundException(String type, String message) {
        super(type, message);
    }
}
