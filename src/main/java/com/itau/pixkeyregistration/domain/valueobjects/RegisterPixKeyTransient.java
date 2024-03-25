package com.itau.pixkeyregistration.domain.valueobjects;

import com.itau.pixkeyregistration.domain.pix.enums.PixKeyType;

public record RegisterPixKeyTransient(
        PixKeyType type,
        String value,
        RegisterPixKeyAccountDetailsTransient accountDetails
) {
}


