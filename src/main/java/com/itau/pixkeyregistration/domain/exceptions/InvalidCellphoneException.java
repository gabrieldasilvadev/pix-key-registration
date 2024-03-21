package com.itau.pixkeyregistration.domain.exceptions;

public class InvalidCellphoneException extends DomainException{
    public InvalidCellphoneException(String type, String message) {
        super(type, message);
    }
}
