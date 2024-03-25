package com.itau.pixkeyregistration.domain.exceptions;

public class InvalidCellphonePixKeyException extends DomainException {
    public InvalidCellphonePixKeyException(String type, String message) {
        super(type, message);
    }
}
