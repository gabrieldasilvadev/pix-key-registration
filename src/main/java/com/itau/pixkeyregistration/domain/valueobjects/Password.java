package com.itau.pixkeyregistration.domain.valueobjects;

import com.itau.pixkeyregistration.domain.exceptions.InvalidEmailException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPasswordException;
import lombok.Builder;

import java.util.regex.Pattern;

@Builder
public record Password(String value) {
    public Password {
        validatePassword();
    }

    private void validatePassword() {
        eightCharacters();
        hasCapitalLetter();
        hasSmallLetterCase();
        hasNumber();
    }

    private void eightCharacters() {
        String REGEX_EIGHT_CHARACTERS = "^.{8,}$";
        if (!Pattern.matches(REGEX_EIGHT_CHARACTERS, this.value)) {
            throw new InvalidEmailException(
                    "INVALID_PASSWORD",
                    "The password must have up to 8 characters"
            );
        }
    }

    private void hasCapitalLetter() {
        String REGEX_CAPITAL_LETTER = ".*[A-Z].*";
        if (!Pattern.matches(REGEX_CAPITAL_LETTER, this.value)) {
            throw new InvalidPasswordException(
                    "INVALID_PASSWORD",
                    "The password must have at least one capital letter"
            );
        }
    }

    private void hasSmallLetterCase() {
        String REGEX_SMALL_LETTER = ".*[a-z].*";
        if (!Pattern.matches(REGEX_SMALL_LETTER, this.value)) {
            throw new InvalidPasswordException(
                    "INVALID_PASSWORD",
                    "The password must have at least one small letter"
            );
        }
    }

    private void hasNumber() {
        String REGEX_HAS_NUMBER = ".*\\d.*";
        if (!Pattern.matches(REGEX_HAS_NUMBER, this.value)) {
            throw new InvalidPasswordException(
                    "INVALID_PASSWORD",
                    "The password must have at least one number"
            );
        }
    }
}
