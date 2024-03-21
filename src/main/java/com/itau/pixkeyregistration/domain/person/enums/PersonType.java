package com.itau.pixkeyregistration.domain.person.enums;

public enum PersonType {
    LEGAL_PERSON, NATURAL_PERSON;

    public static PersonType getByPersonalDocument(String personalDocument) {
        if (personalDocument.matches(REGEX_NATURAL_PERSON)) {
            return NATURAL_PERSON;
        } else if (personalDocument.matches(REGEX_LEGAL_PERSON)) {
            return LEGAL_PERSON;
        } else {
            throw new IllegalArgumentException("Documento pessoal inválido");
        }
    }

    private static final String REGEX_NATURAL_PERSON = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
    private static final String REGEX_LEGAL_PERSON = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}";
}
