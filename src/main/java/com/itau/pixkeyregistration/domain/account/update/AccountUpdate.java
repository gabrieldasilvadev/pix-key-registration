package com.itau.pixkeyregistration.domain.account.update;

import com.itau.pixkeyregistration.domain.account.enums.AccountType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountUpdate {
    AccountType accountType;
    String agencyNumber;
    String accountNumber;
    String accountId;
}
