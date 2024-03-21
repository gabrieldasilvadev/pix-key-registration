package com.itau.pixkeyregistration.domain.valueobjects;

import com.itau.pixkeyregistration.domain.exceptions.InvalidEmailException;
import lombok.Builder;

import java.util.regex.Pattern;

@Builder
public record Email(String value) {
    public Email {
        validateEmail();
    }

    private void validateEmail() {
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
}
