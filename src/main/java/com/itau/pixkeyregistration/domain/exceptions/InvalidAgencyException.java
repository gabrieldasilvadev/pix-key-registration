package com.itau.pixkeyregistration.domain.exceptions;

public class InvalidAgencyException extends DomainException {
    public InvalidAgencyException(String type, String message) {
        super(type, message);
    }
}
