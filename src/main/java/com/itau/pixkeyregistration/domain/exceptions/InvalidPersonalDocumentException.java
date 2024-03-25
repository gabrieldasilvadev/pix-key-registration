package com.itau.pixkeyregistration.domain.exceptions;

public class InvalidPersonalDocumentException extends DomainException {
    public InvalidPersonalDocumentException(String type, String message) {
        super(type, message);
    }
}
