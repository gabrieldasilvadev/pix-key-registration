package com.itau.pixkeyregistration.domain.valueobjects;

import com.itau.pixkeyregistration.domain.account.enums.AccountType;

public record AccountUpdateTransient(
        AccountType accountType,
        String agencyNumber,
        String accountNumber
) {
}
