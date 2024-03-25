package com.itau.pixkeyregistration.domain.person.enums;

public enum PersonType {
    LEGAL_PERSON, NATURAL_PERSON;

    public static PersonType getByPersonalDocument(String personalDocument) {
        if (personalDocument.length() == 11) {
            return NATURAL_PERSON;
        } else if (personalDocument.length() == 14) {
            return LEGAL_PERSON;
        } else {
            throw new IllegalArgumentException("Invalid personal document");
        }
    }
}
