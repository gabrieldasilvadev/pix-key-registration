package com.itau.pixkeyregistration.domain.pix.inactive;

import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PixKeyInactiveAccountDetails {
    AccountType accountType;
    String agency;
    String accountNumber;
    String firstName;
    String lastName;
    String accountId;
}
