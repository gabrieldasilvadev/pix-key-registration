package com.itau.pixkeyregistration.domain.valueobjects;

import com.itau.pixkeyregistration.domain.exceptions.InvalidCellphoneException;
import lombok.Builder;

import java.util.regex.Pattern;

@Builder
public record Cellphone(String value) {
    public Cellphone {
        validateCellphone();
    }

    private void validateCellphone() {
        isValidPattern();
    }

    private void isValidPattern() {
        String REGEX_CELLPHONE_PATTERN = "^\\(?([1-9]{2})\\)?(9)?([0-9]{4})-?([0-9]{4})$";
        if (!Pattern.matches(REGEX_CELLPHONE_PATTERN, this.value)) {
            throw new InvalidCellphoneException(
                    "INVALID_CELLPHONE",
                    "The cellphone provided is invalid"
            );
        }
    }
}
