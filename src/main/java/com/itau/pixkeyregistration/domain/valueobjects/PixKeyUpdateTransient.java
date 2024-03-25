package com.itau.pixkeyregistration.domain.valueobjects;

import com.itau.pixkeyregistration.domain.account.enums.AccountType;

public record PixKeyUpdateTransient(
        String keyId,
        AccountType accountType,
        String agencyNumber,
        String accountNumber,
        String firstName,
        String lastName,
        String keyValue
) {
}
