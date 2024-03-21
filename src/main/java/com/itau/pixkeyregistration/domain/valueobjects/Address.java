package com.itau.pixkeyregistration.domain.valueobjects;

public record Address(
        String city,
        String state,
        String neighbourhood,
        String number,
        String complement
) {
}
