package com.itau.pixkeyregistration.domain.valueobjects;

import com.itau.pixkeyregistration.domain.account.enums.PersonalDocumentTypeEnum;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPersonalDocumentException;

import java.util.regex.Pattern;

public record PersonalDocument(String value) {

    public PersonalDocument(String value) {
        this.value = value;
        validatePersonalDocument();
    }

    public static PersonalDocumentTypeEnum fromValue(String value) {
        if (isValidCPF(value)) {
            return PersonalDocumentTypeEnum.CPF;
        } else if (isValidCNPJ(value)) {
            return PersonalDocumentTypeEnum.CNPJ;
        } else {
            return null;
        }
    }

    public PersonalDocumentTypeEnum getType() {
        if (!isValidCNPJ(this.value) && !isValidCPF(this.value)) {
            throw new InvalidPersonalDocumentException(
                    "INVALID_PERSONAL_DOCUMENT",
                    "Personal document informed is invalid"
            );
        }

        return PersonalDocument.fromValue(this.value);
    }

    private void validatePersonalDocument() {
        if (!isValidCPF(this.value)) {
            if (!isValidCNPJ(this.value)) {
                throw new InvalidPersonalDocumentException(
                        "INVALID_PERSONAL_DOCUMENT",
                        String.format("Personal document informed is not valid for create PersonType[%s]",
                                this.getType().name())
                );
            }
        }
    }

    private static boolean isValidCPF(String personalDocument) {
        String REGEX_CPF_PATTERN = "^\\d{11}$";
        return Pattern.matches(REGEX_CPF_PATTERN, personalDocument);
    }

    private static boolean isValidCNPJ(String cnpj) {
        String REGEX_CNPJ_PATTERN = "^\\d{14}$";
        return Pattern.matches(REGEX_CNPJ_PATTERN, cnpj);
    }
}


