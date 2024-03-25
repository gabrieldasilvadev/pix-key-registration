package com.itau.pixkeyregistration.domain.valueobjects;

import com.itau.pixkeyregistration.domain.exceptions.InvalidEmailException;

import java.util.regex.Pattern;

public record Email(String value) {
    public Email(String value) {
        this.value = value;
        validateEmail();
    }

    public void validateEmail() {
        exceededCharactersNumber();
        isValidPattern();
    }

    private void isValidPattern() {
        String REGEX_EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.matches(REGEX_EMAIL_PATTERN, this.value)) {
            throw new InvalidEmailException(
                    "INVALID_EMAIL",
                    "The email provided is not valid"
            );
        }
    }

    private void exceededCharactersNumber() {
        if (this.value.length() > 77) {
            throw new InvalidEmailException(
                    "INVALID_EMAIL",
                    "Email exceeded character limit"
            );
        }
    }
}
