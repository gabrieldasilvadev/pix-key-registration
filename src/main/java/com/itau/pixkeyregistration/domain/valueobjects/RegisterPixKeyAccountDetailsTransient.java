package com.itau.pixkeyregistration.domain.valueobjects;

import com.itau.pixkeyregistration.domain.account.enums.AccountType;

public record RegisterPixKeyAccountDetailsTransient(
        String firstName,
        String lastName,
        String accountNumber,
        AccountType accountType

) {
}